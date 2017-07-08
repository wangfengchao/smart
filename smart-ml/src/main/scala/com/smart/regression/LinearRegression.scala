package com.smart.regression

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.regression.LinearRegressionModel
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LabeledPoint, LinearRegressionWithSGD}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fc.w on 2017/6/28.
  */
object LinearRegression {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Linear_Regression").setMaster("local[1]")
    val sc = new SparkContext(conf)

    Logger.getRootLogger.setLevel(Level.WARN)

    val path = System.getProperty("user.dir")
    val lines = sc.textFile(path + "/smart-ml/src/main/resources/data/linear_regression.txt")
    val data = lines.map( line => {
      val parts = line.split(",")
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(" ").map(_.toDouble)))
    }) // 归一化
      .map(f => LabeledPoint(f.label / 540, Vectors.dense(f.features(0) / 3000, f.features(1) / 4))).cache()

    val numRecord = data.count()

    // 新建线性回归模型， 并设置训练模型
    val numIterations = 120
    val stepSize = 1
    val miniBatchFration = 0.4
    val model = LinearRegressionWithSGD.train(data, numIterations, stepSize, miniBatchFration)


    // 对样本进行测试
    val prediction = model.predict(data.map(_.features))
    val predictionAndLable = prediction.zip(data.map(_.label))
//    predictionAndLable.foreach(pre => println("预测值：" + pre._1 +"   实际值："+ pre._2))
    println("\n实际值:")
    println("size: " + data.collect().size+"")
//    data.collect().foreach(a => print(a +","))
    data.collect().foreach(a => print(a.label+","))
    println("\n")


    println("\n预测值：\n")
    println("size: " + prediction.collect().size+"\n")
    prediction.collect().foreach(a => print(a +","))
    println("\n\n")

    // 计算测试误差 损失函数
    val loss = predictionAndLable.map {
      case (p, l) =>
        val err = p - l
        err * err
    }.reduce(_ + _)
    //均方根误差
    val rmse = math.sqrt(loss / numRecord)
    println(s"Test  RMSE = ${rmse}.")
    println("weights: " + model.weights)
    println("intercept: " + model.intercept)




    data.collect().foreach(l => {
      val arr = l.features.toArray
      print(arr(0)+", ")
    })
    println("\n")
    data.collect().foreach(l => {
      val arr = l.features.toArray
      print(arr(1)+", ")
    })





    // 保存模型
    val ModelPath = "hdfs://h1:8020/data/LinearRegressionModel"
    model.save(sc, ModelPath)

    // 加载模型
    val  sameModel = LinearRegressionModel.load(ModelPath)
    println("weights: " + model.weights)
    println("intercept: " + model.intercept)

  }

}
