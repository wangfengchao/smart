package com.smart.recommend

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.hive.HiveContext
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
    val conf = new SparkConf().setAppName("LifangALS")
    val sc = new SparkContext(conf)
    val sqlContext = new HiveContext(sc)

    // 加载用户行为数据
    val path = System.getProperty("user.dir")
    //"${path}/smart-ml/src/main/resources/data/recommend/lifang/test.txt"
    val myRatings = loadRatings(s"${args(0)}/test.txt")
    val myRatingsRDD = sc.parallelize(myRatings, 1)

    // 记载样本评分数据
    // ${path}/smart-ml/src/main/resources/data/recommend/lifang/score.dat
    val scoreDir = s"${args(0)}/score.dat"
    val scoreRDD = sc.textFile(scoreDir).map(line => {
      val fields = line.split("\u0001")
      val n = (new util.Random).nextInt(10)
//      println(fields(0), fields(2), fields(3))
      //(随机数，(id, houseId, score))
      (n, Rating(fields(0).toInt, fields(2).toInt, fields(3).toDouble))
    })

    // 加载房源数据
    // ${path}/smart-ml/src/main/resources/data/recommend/lifang/house.txt
    val houseDir = s"${args(0)}/house.txt"
    val houses = sc.textFile(houseDir).map(line => {
      val fields = line.split("\t")
      // (houseId, 房屋类型，面积， 区域， 价格, 卧室数，客厅数, 卫生间数，ID， 城市ID， 名称， 区域id)
      (fields(1).toInt, (fields(1), fields(3), fields(4), fields(6), fields(7),  fields(10), fields(11), fields(12), fields(14), fields(15), fields(16), fields(17)))
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
    val ranks = List(4, 8, 12, 16, 20)
    val lambdas = List(0.01, 0.03, 0.06, 0.09, 0.12)
    val numIters = List(10, 15, 20, 25, 30)
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
      .take(100)

    //推荐结果保存
    import sqlContext.implicits._
    sc.parallelize(recommendations).map(f => f.product)
      .toDF.registerTempTable("tmp_recomm_table")

    //用户行为加载, 插入数据
    val userBeData = loadUserBehaviorData(s"${args(0)}/test.txt")
    userBeData.foreach(record => {
      val recommSQL = "insert overwrite table lifang.user_recommend  partition(guid = \""+ record._1 +"\") select * from tmp_recomm_table "
      sqlContext.sql(recommSQL)
    })

    //用户浏览房源平均房价
    println("User behavior :")
    val svgPrice = userBehaviorSellPriceAvg(userBeData, sqlContext)
    val recommendSQL = "select h.cityId, h.parentId, a.name, h.houseid, h.estateId, h.spaceArea, h.sellprice, h.livingRoomSum from lifang.user_recommend d, lifang.house_detail_info h , lifang.area_info a " +
      " where h.houseid = d.houseid and h.parentId = a.id and h.sellprice between " + (svgPrice._1 - 70) + " and "+ (svgPrice._1 + 70) +" and h.spaceArea between "+ (svgPrice._2 - 30) +" and "+ (svgPrice._2 + 30)

    val result = sqlContext.sql(recommendSQL).collect()
    println("Houses recommended for you:")
    result.foreach(row => {
      println( s"城市ID: ${row.getInt(0)}  区域名称: ${row.getString(2)}  小区ID：${row.getInt(4)}  房源ID：${row.getInt(3)}   面积： ${row.getDouble(5)}  售价：${ row.getDouble(6)}  房间数量： ${row.getInt(7)}" )
    })

//    var i = 1
//    println("Houses recommended for you:")
//    recommendations.foreach { r =>
//      println("%2d".format(i) + ": " + houses(r.product))
//      i += 1
//    }

    sc.stop()
  }

  /**
    * 计算用户浏览房源的平均房价
    * @param userBeData
    * @return 平均房价
    */
  def userBehaviorSellPriceAvg(userBeData: Set[(String, String)], sqlContext: HiveContext): (Double, Double) = {
    val houseIds = new StringBuilder()
    userBeData.foreach(record => houseIds.append(record._2 + ","))
    var houseIdStr = houseIds.toString()
    houseIdStr = houseIdStr.substring(0, houseIdStr.length - 1)

    val sql = "select h.cityId, h.parentId, a.name, h.houseid, h.estateId, h.spaceArea, h.sellprice, h.livingRoomSum from lifang.house_detail_info h , lifang.area_info a where  h.parentId = a.id  and h.houseid in ("+ houseIdStr +")"
    val result = sqlContext.sql(sql).collect()
    result.foreach(row => {
      println( s"城市ID: ${row.getInt(0)}  区域名称: ${row.getString(2)}  小区ID：${row.getInt(4)}  房源ID：${row.getInt(3)}   面积： ${row.getDouble(5)}  售价：${ row.getDouble(6)}  房间数量： ${row.getInt(7)}" )
    })
    val count = result.size
    val sumPrice =  result.map(_.getDouble(6)).reduce(_+_)
    val sumArea = result.map(_.getDouble(5)).reduce(_+_)
    val avgPrice = sumPrice / count
    val avgArea = sumArea / count
    (avgPrice, avgArea)
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

  /**
    * 加载用户行为数据
    * @param path
    * @return (guid, houseId)
    */
  def loadUserBehaviorData(path: String): Set[(String, String)] = {
    val lines = Source.fromFile(path).getLines()
    val ratings = lines.map(line => {
      val fields = line.split("\u0001")
      (fields(1), fields(2))
    })

    ratings.toSet
  }

}
