package com.smart.features

import org.apache.spark.ml.feature._
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.sql.functions._
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
/**
  * 特征转换
  * 20种特征转换
  * Created by fc.w on 2017/5/24.
  */
object Transforming {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Transforming")
    val sc = new SparkContext(conf)
    val sqlContext = new HiveContext(sc)

  }

  /**
    * 分词器
    * @param sqlContext
    */
  def tokenization(sqlContext: HiveContext): Unit = {
    val sentenceDataFrame = sqlContext.createDataFrame(Seq(
      (0, "Hi I heard about Spark"),
      (1, "I wish Java could use case classes"),
      (2, "Logistic,regression,models,are,neat")
    )).toDF("id", "sentence")

    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
    val regexTokenizer = new RegexTokenizer().setInputCol("sentence").setOutputCol("words").setPattern("\\W")

    val countTokens = udf{ (words: Seq[String]) => words.length }

    val tokenized = tokenizer.transform(sentenceDataFrame)
    tokenized.select("sentence", "words").withColumn("tokens", countTokens(col("words"))).show(false)

    val regexTokenized = regexTokenizer.transform(sentenceDataFrame)
    regexTokenized.select("sentence", "words").withColumn("tokens", countTokens(col("words"))).show(false)
  }

  /**
    *
    * @param sqlContext
    */
  def polynomialExpansion(sqlContext: HiveContext) = {
    val data = Array(
      Vectors.dense(2.0, 1.0),
      Vectors.dense(0.0, 0.0),
      Vectors.dense(3.0, -1.0)
    )
    val df = sqlContext.createDataFrame(data.map(Tuple1.apply)).toDF("features")
    val polyExpansion = new PolynomialExpansion().setInputCol("features").setOutputCol("polyFeatures").setDegree(3)
    val polyDF = polyExpansion.transform(df)
    polyDF.show(false)
  }

  /**
    *
    * @param sqlContext
    */
  def discreteCosineTransform(sqlContext: HiveContext) = {
    val data = Seq(
      Vectors.dense(0.0, 1.0, -2.0, 3.0),
      Vectors.dense(-1.0, 2.0, 4.0, -7.0),
      Vectors.dense(14.0, -2.0, -5.0, 1.0))

    val df = sqlContext.createDataFrame(data.map(Tuple1.apply)).toDF("features")
    val dct = new DCT().setInputCol("features") .setOutputCol("featuresDCT").setInverse(false)
    val dctDf = dct.transform(df)
    dctDf.select("featuresDCT").show(false)
  }

  def minMaxScaler(sqlContext: HiveContext) = {
    val houseDF = sqlContext.sql("select houseid, townid, sellprice, spacearea, bedroomsum from lifang.house_matrix where cityid=43 limit 10")
    val vectorRDD = houseDF.map(row => {(row.getLong(0),row.getLong(1), Vectors.dense(row.getDouble(2),row.getDouble(3), row.getInt(4))) })
    val dataFrame = sqlContext.createDataFrame(vectorRDD).toDF("houseid", "townid", "features")

    val scaler = new MinMaxScaler().setInputCol("features").setOutputCol("scaledFeatures")

    val scalerModel = scaler.fit(dataFrame)
    // rescale each feature to range [min, max].
    val scaledData = scalerModel.transform(dataFrame)
    println(s"Features scaled to range: [${scaler.getMin}, ${scaler.getMax}]")
    scaledData.select("features", "scaledFeatures").show()
  }

}
