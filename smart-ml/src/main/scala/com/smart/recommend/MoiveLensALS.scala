package com.smart.recommend

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

/**
  * Created by fc.w on 2017/8/2.
  */
object MoiveLensALS {

  def main(args: Array[String]): Unit = {
    // 屏蔽不必要的日志显示在终端上
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.apache.eclipse.jetty.server").setLevel(Level.OFF)

    // 设置运行环境
    val conf = new SparkConf().setAppName("MoiveLensALS").setMaster("local[5]")
    val sc = new SparkContext(conf)

    // 装载用户评分数据
    val path = System.getProperty("user.dir")
    val myRatings = loadRatings(s"${path}/smart-ml/src/main/resources/data/recommend/ml-1m/personalRatings.txt")
    val myRatingsRDD = sc.parallelize(myRatings, 1)

    // 装载样本评分数据文件
    val movielensHomeDir = s"${path}/smart-ml/src/main/resources/data/recommend/ml-1m/ratings.dat"
    val ratings = sc.textFile(movielensHomeDir).map(line => {
      val fields = line.split("::")
      // 最后一列Timestamp取除10的余数作为key，Rating为值，即(Int，Rating)
      (fields(3).toLong % 10, Rating(fields(0).toInt, fields(1).toInt, fields(2).toFloat))
    })

    // 装载电影目录对照表(电影ID->电影标题)
    val movieMenuDir = s"${path}/smart-ml/src/main/resources/data/recommend/ml-1m/movies.dat"
    val movies = sc.textFile(movieMenuDir).map(line => {
      val fields = line.split("::")
      (fields(0).toInt, fields(1))
    }).collect().toMap

    // 统计用户对电影的评分数目、有用户数量、电影数量
    val numRatings = ratings.count()
    val numUsers = ratings.map(_._2.user).distinct().count()
    val numMovies =  ratings.map(_._2.product).distinct().count()
    println("Got " + numRatings + " ratings from " + numUsers + " users " + numMovies + " movies")

    // 将样本评分表以key值切分成3个部分，分别用于训练 (60%，并加入用户评分), 校验 (20%), and 测试 (20%)
    // 该数据在计算过程中要多次应用到，所以cache到内存
    val numPartitions = 4
    val training = ratings.filter(x => x._1 < 6).values.union(myRatingsRDD).repartition(numPartitions).persist()
    val validation = ratings.filter(x => x._1 >= 6 && x._1 > 8).values.union(myRatingsRDD).repartition(numPartitions).persist()
    val test = ratings.filter(x => x._1 >= 8).values.persist()

    val numTraining = training.count()
    val numValidation = validation.count()
    val numTest = test.count()
    println("Training: " + numTraining + " validation: " + numValidation + " test: " + numTest)

    // 2. 训练不同参数下的模型，并在校验集中验证，获取最佳参数下的模型
    val ranks = List(8, 12)
    val lambdas = List(0.1, 10.0)
    val numIters = List(10, 20)
    var bestModel: Option[MatrixFactorizationModel] = None
    var bestValidationRmse = Double.MaxValue
    var bestRank = 0
    var bestLambda = -1.0
    var bestNumIter = -1

    for (rank <- ranks; lambda <- lambdas; numIter <- numIters) {
      // 训练模型
      val model = ALS.train(training, rank, numIter, lambda)
      // 2.1 计算均方根误差, 用训练模型和校验集合计算
      val validationRmse = computeRmse(model, validation, numValidation)
      println("RMSE(validation) = " + validationRmse + " for the model trained with rank = "  + rank + ",lambda = " + lambda + ",and numIter = " + numIter + ".")

      if (validationRmse < bestValidationRmse) {
        bestModel = Some(model)
        bestValidationRmse = validationRmse
        bestRank = rank
        bestLambda = lambda
        bestNumIter = numIter
      }
    }

    // 3. 用最佳模型预测   测试集   的评分，并计算和实际评分之间的均方根误差（RMSE）
    val testRMSE = computeRmse(bestModel.get, test, numTest)
    println("The best model was trained with rank = " + bestRank + " and lambda = " + bestLambda
      + ", and numIter = " + bestNumIter + ", and its RMSE on the test set is " + testRMSE + ".")

    /* create a naive baseline and compare it with the best model */
    // 训练集和校验集合并，对评分求均值
    val meanRating = training.union(validation).map(_.rating).mean()
    // 训练集评分均值 与 测试集评分 计算RMSE
    val baselineRmse = math.sqrt(test.map(x => (meanRating - x.rating) * (meanRating - x.rating)).reduce(_ + _) / numTest)

    // 最好基线RMSE - 测试RMSE
    val improvement = (baselineRmse - testRMSE) / baselineRmse * 100
    println("The best model improves the baseline by " + "%1.2f".format(improvement) + "%.")

    // 推荐前十部最感兴趣的电影，注意要剔除用户已经评分的电影
    val myRatedMovieIds = myRatings.map(_.product).toSet
    val candidates = sc.parallelize(movies.keys.filter(! myRatedMovieIds.contains(_)).toSeq)
    // 预测
    val recommendations = bestModel.get
      .predict(candidates.map((0, _)))
      .collect
      .sortBy(-_.rating)
      .take(10)

    var i = 1
    println("Movies recommended for you:")
    recommendations.foreach { r =>
      println("%2d".format(i) + ": " + movies(r.product))
      i += 1
    }

    sc.stop()
  }

  /**
    * 校验集预测数据和实际数据之间的均方根误差
    * @param model 训练模型数据
    * @param data 校验数据集
    * @param n 校验数据集数量， 观测次数n
    * @return
    */
  def computeRmse(model: MatrixFactorizationModel, data: RDD[Rating],n: Long): Double = {
    val predictions: RDD[Rating] = model.predict((data.map(x => (x.user, x.product))))
    val predictionsAndRatings = predictions.map(x => ((x.user, x.product), x.rating)).join(data.map(x => ((x.user, x.product), x.rating))).values
    math.sqrt(predictionsAndRatings.map(x => (x._1 - x._2) * (x._1- x._2)).reduce(_+_) / n)
  }


  /** 装载用户评分文件 personalRatings.txt **/
  def loadRatings(path: String): Seq[Rating] = {
    val lines = Source.fromFile(path).getLines()
    val ratings = lines.map(line => {
      val fields = line.split("::")
      Rating(fields(0).toInt, fields(1).toInt, fields(2).toFloat)
    }).filter(_.rating > 0.0)

    if (ratings.isEmpty) {
      sys.error("No ratings provided.")
    } else {
      ratings.toSeq
    }

  }

}
