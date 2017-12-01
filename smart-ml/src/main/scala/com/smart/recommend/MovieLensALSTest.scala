package com.smart.recommend

import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fc.w on 2017/10/28.
  */
object MovieLensALSTest {
  val implicitPrefs: Boolean = true

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("MovieLensALS-Test").setMaster("local[2]")
    val sc = new SparkContext(conf)
    sc.setLogLevel("WARN")

    val path = System.getProperty("user.dir")
    val ratings = sc.textFile(s"${path}/smart-ml/src/main/resources/data/recommend/ml-1m/sample_movielens_ratings.txt")
      .map(line => {
        val fields = line.split("::")
        // 判断是否有负评分
        if (implicitPrefs) {
          /*
           * MovieLens ratings are on a scale of 1-5:
           * 5: Must see
           * 4: Will enjoy
           * 3: It's okay
           * 2: Fairly bad
           * 1: Awful
           * So we should not recommend a movie if the predicted rating is less than 3.
           * To map ratings to confidence scores, we use
           * 5 -> 2.5, 4 -> 1.5, 3 -> 0.5, 2 -> -0.5, 1 -> -1.5. This mappings means unobserved
           * entries are generally between It's okay and Fairly bad.
           * The semantics of 0 in this expanded world of non-positive weights
           * are "the same as never having interacted at all".
           */
          // 为每一行创建Rating
          Rating(fields(0).toInt, fields(1).toInt, fields(2).toDouble - 2.5)
        } else {
          Rating(fields(0).toInt, fields(1).toInt, fields(2).toDouble)
        }
      }).cache()

    // 统计总数，用户数量，评分数量
    val numRatings = ratings.count()
    val numUsers = ratings.map(_.user).distinct().count()
    val numMovies = ratings.map(_.product).distinct().count()

    println(s"Got $numRatings ratings from $numUsers users on $numMovies movies.")

    // 按照权重切分rdd
    val splits = ratings.randomSplit(Array(0.8, 0.2))
    // 用80%的数据作为训练集
    val training = splits(0).cache()
    // 用20%的数据作为测试集
    val test = if (implicitPrefs) {
      /*
       * 0 means "don't know" and positive values mean "confident that the prediction should be 1".
       * Negative values means "confident that the prediction should be 0".
       * We have in this case used some kind of weighted RMSE. The weight is the absolute value of
       * the confidence. The error is the difference between prediction and either 1 or 0,
       * depending on whether r is positive or negative.
       */
      splits(1).map(x => Rating(x.user, x.product, if (x.rating > 0) 1.0 else 0.0))
    } else {
      splits(1)
    }.cache()

    val numTraining = training.count()
    val numTest = test.count()
    println(s"Training: $numTraining, test: $numTest.")

    ratings.unpersist(blocking = false)

    val model = new ALS()
      .setRank(10)  // 矩阵分解的隐含分类为10
      .setIterations(10) // 迭代次数为10
      .setLambda(1) // 正则项lambda参数为1
      .setImplicitPrefs(implicitPrefs)
      .run(training)

    // 计算模型的准确度
    val rmse1 = computeRmse(model, training, implicitPrefs)
    val rmse = computeRmse(model, test, implicitPrefs)
    println(s"Test RMSE = $rmse1.")
    println(s"Test RMSE = $rmse.")

    sc.stop()
  }

  def computeRmse(model: MatrixFactorizationModel, data: RDD[Rating], implicitPrefs: Boolean): Double = {
    def mapPredictedRating(r: Double): Double = {
      if (implicitPrefs) math.max(math.min(r, 1.0), 0.0) else r
    }

    val predictions: RDD[Rating] = model.predict(data.map(x => (x.user, x.product)))
    val predictionsAndRatings = predictions.map{ x =>
      ((x.user, x.product), mapPredictedRating(x.rating))
    }.join(data.map(x => ((x.user, x.product), x.rating))).values

    math.sqrt(predictionsAndRatings.map(x => (x._1 - x._2) * (x._1 - x._2)).mean())
  }



}
