package com.smart.tf_idf

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * TF-IDF 测试
  * http://lxw1234.com/archives/2016/01/605.htm
  * Created by fc.w on 2017/6/22.
  */
object TF_IDF {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("TF-IDF Test").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    // 隐式转换 from RDDs to DataFrames
    import sqlContext.implicits._

    Logger.getRootLogger.setLevel(Level.WARN)

    /* 1. 读取样本数据，转化为DF */
    val path = System.getProperty("user.dir")
    val data = sc.textFile(path + "/smart-ml/src/main/resources/data/tf-idf.txt")
    val dataDF = data.map(x => {
      val record = x.split(",")
      RawDataRecord(record(0), record(1))
    }).toDF()

    dataDF.select("category", "text").take(2).foreach(println)

    /* 2. 将分词转换为数组 */
    val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
    val wordsData = tokenizer.transform(dataDF)
    wordsData.select($"category", $"text", $"words").take(2).foreach(println)

    /* 3. 将每个词转换为Int类型， 并计算其在文档中的词频（TF）.
     * 中文词语转换成INT型的Hashing算法.
     * setNumFeatures(100)表示将Hash分桶的数量设置为100个,一般来说，这个值越大，不同的词被计算为一个Hash值的概率就越小，数据也更准确，但需要消耗更大的内存，和Bloomfilter是一个道理*/
    val hashingTF = new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(100)
    val featurizedData = hashingTF.transform(wordsData)

    featurizedData.select($"category", $"words", $"rawFeatures").take(2).foreach(println)
    // 结果中，“苹果”用23来表示，第一个文档中，词频为2，第二个文档中词频为1.

    /* 4. 计算TF-IDF */
    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel = idf.fit(featurizedData)
    val rescaledData = idfModel.transform(featurizedData)
    rescaledData.select($"category", $"words", $"features").take(2).foreach(println)

    /* 5. 转换成Bayes的输入格式 */
    val trainDataRdd = rescaledData.select($"category", $"features").map {
      case Row(label: String, features: Vector) => LabeledPoint(label.toDouble, Vectors.dense(features.toArray))
    }

    trainDataRdd.foreach(println)

  }

}

case class RawDataRecord(category: String, text: String)