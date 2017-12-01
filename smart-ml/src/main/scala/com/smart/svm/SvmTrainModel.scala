package com.smart.svm

import org.apache.spark.{SparkConf, SparkContext}

import org.apache.spark.mllib.classification.{SVMModel, SVMWithSGD}
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.util.MLUtils


/**
  * Created by fc.w on 2017/11/20.
  */
object SvmTrainModel {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SVMWithSGDExample").setMaster("local")
    val sc = new SparkContext(conf)

    // 1.加载数据集sample_libsvm_data.txt  svm_data.txt
    val path = System.getProperty("user.dir")
    val data = MLUtils.loadLibSVMFile(sc, path + "/smart-ml/src/main/resources/data/svm_data.txt")
    data.take(2).foreach(println)

    // 2.将数据分为训练集(60%)和测试集(40%)。
    val splits = data.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0).cache()
    val test = splits(1)

    // 3.运行训练算法，构建模型
    val numIterations = 100
    val model = SVMWithSGD.train(training, numIterations)

    // 4.清除默认阈值
    model.clearThreshold()

    // 5.使用测试集预测得分
    val scoreAndLabels = test.map { point =>
      val score = model.predict(point.features)
      (score, point.label)
    }

//    scoreAndLabels.foreach(record => println("预测标签： " + record._1 + "     实际标签： " + record._2))

    // Get evaluation metrics.
    val metrics = new BinaryClassificationMetrics(scoreAndLabels)
    val auROC = metrics.areaUnderROC()
    metrics.recallByThreshold().foreach(record => println("预测标签： " + record._1 + "     实际标签： " + record._2))
    println("Area under ROC = " + auROC)
    //Area under ROC = 1.0
    // Save and load model
//    model.save(sc, "target/tmp/scalaSVMWithSGDModel")
//    val sameModel = SVMModel.load(sc, "target/tmp/scalaSVMWithSGDModel")


    sc.stop()
  }

}
