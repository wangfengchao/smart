package com.smart.bayes

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 朴素贝叶斯
  * 数据集：汽车相关文档
  * Created by fc.w on 2017/6/22.
  */
object NaiveBayes_Car {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("NaiveBayes_Car").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    Logger.getRootLogger.setLevel(Level.WARN)

    /* 1. 读取样本数据，转化为DF */
    val path = System.getProperty("user.dir")
    val data = sc.textFile(path + "/smart-ml/src/main/resources/data/C000007.txt")
    val parseData = data.map(_.split(",")).map(attrs => RawDataRecord(attrs(0), attrs(1))).toDF()

    /* 2. 样本数据划分为训练样本0.7 和 测试样本0.3 */
    val splits = parseData.randomSplit(Array(0.7, 0.3))
    val trainingDF = splits(0).toDF()
    val testDF = splits(1).toDF()

    /* 3. 将词语转换成数组 */
    val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
    val wordsData = tokenizer.transform(trainingDF)
    println("output1：")
    wordsData.select($"category",$"text",$"words").take(1).foreach(println)

    /* 4. 计算每个词在文档中的词频 */
    val hashingTF = new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(500000)
    val featurizedData = hashingTF.transform(wordsData)
    println("\noutput2：")
    featurizedData.select($"category", $"words", $"rawFeatures").take(1).foreach(println)

    /* 5. 计算每个词的TF-IDF */
    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel = idf.fit(featurizedData)
    val rescaledData = idfModel.transform(featurizedData)
    println("\noutput3：")
    rescaledData.select($"category", $"features").take(1).foreach(println)

    /* 6. 转换成Bayes的输入格式 */
    val trainDataRdd = rescaledData.select($"category", $"features").map {
      case Row(label: String, features: Vector) =>
        LabeledPoint(label.toDouble, Vectors.dense(features.toArray))
    }

    println("\noutput4：")
    trainDataRdd.take(1).foreach(println)

    /* 7. 训练模型 */
    val model = NaiveBayes.train(trainDataRdd, lambda = 1.0, modelType = "multinomial")

    /* 8. 测试数据集，特征表示及格式转换 */
    val testwordsData = tokenizer.transform(testDF)
    val testfeaturizedData = hashingTF.transform(testwordsData)
    val testrescaledData = idfModel.transform(testfeaturizedData)
    val testDataRdd = testrescaledData.select($"category",$"features").map {
      case Row(label: String, features: Vector) =>
        LabeledPoint(label.toDouble, Vectors.dense(features.toArray))
    }

    /* 9. 对测试数据集使用训练模型进行分类预测 */
    val testpredictionAndLabel = testDataRdd.map(p => (model.predict(p.features), p.label))

    /* 10. 统计分类准确率 */
    var testAccuracy = 1.0 * testpredictionAndLabel.filter(x => x._1 == x._2).count() / testDataRdd.count()
    println("output5：")
    println(testAccuracy)

    /* 11. 保存模型 */
    val modelPath = "hdfs://h1:8020/user/root/model/naive_bayes_model"
    model.save(sc, modelPath)

    /* 12. 模型加载 */
    val sameModel = NaiveBayesModel.load(sc, modelPath)

  }

}

case class RawDataRecord(category: String, text: String)
