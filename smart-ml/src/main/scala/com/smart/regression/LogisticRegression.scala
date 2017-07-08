package com.smart.regression

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fc.w on 2017/6/23.
  */
object LogisticRegression {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[1]").setAppName("logistic_regression_launcher")
    val sc = new SparkContext(conf)

    Logger.getRootLogger.setLevel(Level.WARN)

    val path = System.getProperty("user.dir")
    val data = MLUtils.loadLibSVMFile(sc, path + "/smart-ml/src/main/resources/data/sample_libsvm_data.txt")
    data.take(1).foreach(println)

    // 2.0 样本数据划分训练样本和测试样本
    val splits = data.randomSplit(Array(0.6, .04), seed = 11L)
    val training = splits(0).cache()
    val test = splits(1)

    // 3.0 建立逻辑回归模型，并训练
    val numIterations = 100
    val stepSize = 1
    val miniBatchFraction = 0.5
    val model = LogisticRegressionWithSGD.train(training, numIterations, stepSize, miniBatchFraction)
    println(model.weights)
    println(model.intercept)

    // 4.0 对测试样本进行测试
    val predictionAndLabels = test.map {
      case LabeledPoint(label, features) =>
        val prediction = model.predict(features)
        (prediction, label)
    }

    val print_prediction = predictionAndLabels.take(20)
    println("prediction" + "\t" + "lable")
    for (i <- 0 to print_prediction.length - 1) {
      println(print_prediction(i)._1 + "\t\t\t" + print_prediction(i)._2)
    }

    // 5.0 计算误差
    val matrics = new MulticlassMetrics(predictionAndLabels)
    val precision = matrics.precision
    println("Precision: " + precision)

    // 6.0 保存模型
    val ModelPath = "hdfs://h1:8020/user/root/model/logistic_regression_model"
    model.save(sc, ModelPath)

  }

}
