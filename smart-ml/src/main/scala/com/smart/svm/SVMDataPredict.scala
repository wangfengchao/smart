package com.smart.svm

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.classification.SVMModel
import org.apache.spark.mllib.util.MLUtils

/**
  * Created by fc.w on 2017/11/22.
  */
object SVMDataPredict {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SVMWithSGDExample").setMaster("local")
    val sc = new SparkContext(conf)

    // 1.加载预测集
    val path = System.getProperty("user.dir")
    val data = MLUtils.loadLibSVMFile(sc, path + "/smart-ml/src/main/resources/data/svm_data.txt")
    data.take(2).foreach(println)

    // 2. 加载SVM模型
    val sameModel = SVMModel.load(sc, "target/tmp/scalaSVMWithSGDModel")
    data.map(point => {
      // 如果设置阈值，返回值为1.0正类和0.0父类
      val score = sameModel.predict(point.features)
    })

  }

}
