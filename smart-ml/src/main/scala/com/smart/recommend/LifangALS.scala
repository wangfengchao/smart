package com.smart.recommend

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

/**
  * Created by fc.w on 2017/8/3.
  */
object LifangALS {

  def main(args: Array[String]): Unit = {
    // 设置日志级别
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.apache.eclipse.jetty.server").setLevel(Level.OFF)

    // 设置运行环境
    val conf = new SparkConf().setAppName("LifangALS").setMaster("local[5]")
    val sc = new SparkContext(conf)

    // 加载用户评分数据
    val path = System.getProperty("user.dir")
    val myRatings = loadRatings(s"${path}/smart-ml/src/main/resources/data/recommend/lifang/test.txt")
    val myRatingsRDD = sc.parallelize(myRatings, 1)

    // 记载样本评分数据
    val scoreDir = s"${path}/smart-ml/src/main/resources/data/recommend/lifang/score.dat"
    val scoreRDD = sc.textFile(scoreDir).map(line => {
      val fields = line.split("\u0001")
      val n = (new util.Random).nextInt(10)
//      println(fields(0), fields(2), fields(3))
      //(随机数，(id, houseId, score))
      (n, Rating(fields(0).toInt, fields(2).toInt, fields(3).toDouble))
    })

    // 加载房源数据
    val houseDir = s"${path}/smart-ml/src/main/resources/data/recommend/lifang/house.txt"
    val houses = sc.textFile(houseDir).map(line => {
      val fields = line.split("\u0001")
      // (houseId, districtId, cityId)
      (fields(0).toInt, (fields(0), fields(1), fields(8)))
    }).collect().toMap

    // 统计评分数量，用户数量，房源数量
    val ratingsNum = scoreRDD.count()
    val userNum = scoreRDD.map(_._2.user).distinct().count()
    val houseNum = scoreRDD.map(_._2.product).distinct().count()
    println("Got " + ratingsNum + " ratings from " + userNum + " userNum " + houseNum + " houseNum")

    // 将样本集划分为3部分：训练集(60%), 校验集(20%), 测试集(20%)
    val numPartition = 5
    val training = scoreRDD.filter(x => x._1 < 6).values.union(myRatingsRDD).repartition(numPartition).persist()
    val validation = scoreRDD.filter(x => x._1 >= 6 && x._1 < 8).values.union(myRatingsRDD).repartition(numPartition).persist()
    val test = scoreRDD.filter(x => x._1 >= 8).values.persist()

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

    // 3.  用最佳模型预测  测试集 的评分，并计算和实际评分之间的均方根误差（RMSE）
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

    // 推荐前十套最感兴趣的房源，注意要剔除用户已经评分的房源
    val myRatedHouseIds = myRatings.map(_.product).toSet
    val candidates = sc.parallelize(houses.keys.filter(! myRatedHouseIds.contains(_)).toSeq)
    // 预测
    val recommendations = bestModel.get
      .predict(candidates.map((11884, _)))
      .collect
      .sortBy(-_.rating)
      .take(10)

    var i = 1
    println("Houses recommended for you:")
    recommendations.foreach { r =>
      println("%2d".format(i) + ": " + houses(r.product))
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

  /**
    * 加载用户评分数据： id  房源ID  score
    * @param path
    * @return
    */
  def loadRatings(path: String): Seq[Rating] = {
    val lines = Source.fromFile(path).getLines()
    val ratings = lines.map(line => {
      val fields = line.split("\u0001")
      Rating(fields(0).toInt, fields(2).toInt, fields(3).toDouble)
    })

    ratings.toSeq
  }

}
