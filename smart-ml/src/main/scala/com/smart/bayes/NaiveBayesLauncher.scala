package com.smart.bayes

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.{SparkConf, SparkContext}


/**
  * 朴素贝叶斯
  * Created by fc.w on 2017/6/22.
  */
object NaiveBayesLauncher {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[1]").setAppName("NaiveBayes_Launcher")
    val sc = new SparkContext(conf)

    Logger.getRootLogger.setLevel(Level.WARN)

    /* 1. 读取样本数据，封装成LabeledPoint(标签， 特征值Vectors) */
    val path = System.getProperty("user.dir")
    val data = sc.textFile(path + "/smart-ml/src/main/resources/data/naive_bayes_data.txt")
    val parseData = data.map{ line =>
      val parts = line.split(",")
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(" ").map(_.toDouble)))
    }

    /* 2. 样本数据划分为训练样本0.6 和 测试样本0.4 */
    val splits = parseData.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0)
    val test = splits(1)

    /* 3. 建立贝叶斯模型，并训练 */
    val model = NaiveBayes.train(parseData, lambda = 1.0, modelType = "multinomial")
    print(s"model.labels: ")
    model.labels.foreach(ele => print(ele + ", "))
    print(s"\n\n model.pi:")
    model.pi.foreach(ele => print(ele + ", "))
    print(s"\n\n model.modelType: ${model.modelType}")
    print(s"\n\n model.theta:")
    model.theta.foreach(ele => {
      println()
      ele.foreach(e => print(e + ", "))
    })

    /* 4. 对测试样本进行测试 */
    val predictionAndLable = test.map(p => (model.predict(p.features), p.label))
    val print_predict = predictionAndLable.take(20)
    println("\n\nprediction" + "\t" + "label")
    for (i <- 0 to print_predict.length - 1) {
      println(print_predict(i)._1 + "\t" + print_predict(i)._2)
    }

    //统计分类准确率
    val accuracy = 1.0 * predictionAndLable.filter(x => x._1 == x._2).count / test.count()
    println("accuracy: " + accuracy)


    /* 5. 保存模型 */
    val modelPath = "hdfs://h1:8020/user/root/model/naive_bayes_model"
    model.save(sc, modelPath)

    /* 6. 模型加载 */
    val sameModel = NaiveBayesModel.load(sc, modelPath)
    println()
    print(s"model.labels: ")
    model.labels.foreach(ele => print(ele + ", "))
    print(s"\n\n model.pi:")
    model.pi.foreach(ele => print(ele + ", "))
    print(s"\n\n model.modelType: ${model.modelType}")
    print(s"\n\n model.theta:")
    model.theta.foreach(ele => {
      println()
      ele.foreach(e => print(e + ", "))
    })


  }

}
