package com.smart.regression

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LabeledPoint, LinearRegressionWithSGD}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 根据用户浏览，收藏，微聊，约看，带看次数 通过线性回归计算权重
  *
  * Created by fc.w on 2017/7/3.
  */
object LinearRegression_User_Preference {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("user_preference_lr").setMaster("local[1]")
    val sc = new SparkContext(conf)
    Logger.getRootLogger.setLevel(Level.WARN)
    val path = System.getProperty("user.dir")
    val lines = sc.textFile(path + "/smart-ml/src/main/resources/data/user_preference.txt")
    val data = lines.filter(record => {
      val arr = record.split(",")
      arr.length == 6
    }).map(line => {
      val arr = line.split(",")
        LabeledPoint(arr(5).toDouble, Vectors.dense(arr(1).toDouble, arr(2).toDouble, arr(3).toDouble, arr(4).toDouble))
    }).map(f => LabeledPoint(f.label / 15, Vectors.dense(f.features(0) / 562, f.features(1) / 27, f.features(2) / 28, f.features(3) / 17)))

    val numRecord = data.count()
    println("record num: " + numRecord)

    // 新建线性回归模型， 并设置训练模型
    val numIterations = 200
    val stepSize = 2
    val miniBatchFration = 0.4
    val model = LinearRegressionWithSGD.train(data, numIterations, stepSize, miniBatchFration)

    // 对样本进行测试 prediction就是y   y=ax+b
    val prediction = model.predict(data.map(_.features))
    val predictionAndLable = prediction.zip(data.map(_.label))


    println("\n实际值:")
    println("标签值：")
    data.collect().foreach(l => {
      print(l.label+", ")
    })
    println("\n特征值：")
    data.collect().foreach(l => {
      val arr = l.features.toArray
      print(arr(1)+", ")
    })

    println("\n预测值：")
    prediction.collect().foreach(f => print(f  + ", "))
    println("\n")
    predictionAndLable.collect().foreach(l => print(l._1 + ", "))
    println("\n")
    predictionAndLable.collect().foreach(l => print(l._2 + ", "))

    // 计算测试误差 损失函数
    val loss = predictionAndLable.map {
      case (p, l) =>
        val err = p - l
        err * err
    }.reduce(_ + _)

    //均方根误差
    val rmse = math.sqrt(loss / numRecord)
    println(s"\nTest  RMSE = ${rmse}.")
    println("权重值: " + model.weights)
    println("截距值 intercept: " + model.intercept)

  }

}
