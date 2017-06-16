package com.smart.kmeans

import java.io.{File, PrintWriter}

import org.apache.spark.ml.feature.MinMaxScaler
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.{DenseVector, Vectors}
import org.apache.spark.sql.hive.HiveContext

/**
  * Created by fc.w on 2017/5/25.
  */
object Kmeans {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Kmeans-Launcher")
    val sc = new SparkContext(conf)
    val sqlContext = new HiveContext(sc)

    val cityId = if (args.length == 0) 43 else args(0).toInt

    /* 数据归一化 */
    val houseDF = sqlContext.sql(s"select houseid, townid, sellprice, spacearea, bedroomsum from lifang.house_matrix where cityid=${cityId}")
    val vectorRDD = houseDF.map(row => {(row.getLong(0),row.getLong(1), Vectors.dense(row.getDouble(2),row.getDouble(3), row.getInt(4))) })
    val dataFrame = sqlContext.createDataFrame(vectorRDD).toDF("houseid", "townid", "features")
    val scaler = new MinMaxScaler().setInputCol("features").setOutputCol("scaledFeatures")
    val scalerModel = scaler.fit(dataFrame)
    val scaledData = scalerModel.transform(dataFrame)
//    println(s"Features scaled to range: [${scaler.getMin}, ${scaler.getMax}]")
//    val parsedData = scaledData.select("houseid", "townid", "features", "scaledFeatures").map(row => Vectors.dense(row.get(3).asInstanceOf[DenseVector].toArray))
    val parsedData = scaledData.select("houseid", "townid", "features", "scaledFeatures").map(row => {
      val vectors = row.get(3).asInstanceOf[DenseVector].toArray
      (row.get(0), row.get(1), row.get(2), Vectors.dense(vectors))
    })


    /* K-means */
    val numClusters = 18
    var numIterations = 2
    var runs = 2
    val initMode = "k-means||"
    for (j <- 0 until 100) {
      numIterations += 2
      val writer = new PrintWriter(new File(s"/opt/kmeans/data_k${numClusters}_${numIterations}.txt"))
      writer.println(s"===============numClusters = ${numClusters}============= numIterations = ${numIterations} ============== runs = ${runs}=========================================")
      val clusters = new KMeans().setInitializationMode(initMode).setK(numClusters).setMaxIterations(numIterations).run(parsedData.map(_._4))
      writer.println(parsedData.map(v=> s"houseId: ${v._1}   townid: ${v._2}   features:${v._3}  normalization：${v._4}   belong to cluster :" +clusters.predict(v._4)).collect().mkString("\n"))

      // 误差平方和
      val WSSSE = clusters.computeCost(parsedData.map(_._4))
      writer.println(s"WithinSet Sum of Squared Errors = ${WSSSE}")
      println(s"WithinSet Sum of Squared Errors = ${WSSSE}")
      //统计每个类簇出现的次数
      val res = parsedData.map(vector => (clusters.predict(vector._4), 1)).reduceByKey((a, b) => (a + b)).map(f => f._2).collect()
      //打印出每列特征值的中心点
      println("Clustercenters:")
      var i = 0
      val count = clusters.clusterCenters(0).size
      for (center <- clusters.clusterCenters) {
        val sellPrice = BigDecimal(center(0)).setScale(7, BigDecimal.RoundingMode.HALF_UP) / 1000
        val spacearea = BigDecimal(center(1)).setScale(7, BigDecimal.RoundingMode.HALF_UP) / 1000
        val bedroomsum = BigDecimal(center(2)).setScale(4, BigDecimal.RoundingMode.HALF_UP) / 1000
        writer.println(s"\tFeatures Center ${count}:\tsellPrice = ${sellPrice}\tspacearea = ${spacearea}\tbedroomsum = ${bedroomsum}\t    Cluster times: ${res(i)}")
        println(s"\tCenter point:\t${center}")
        i += 1
      }

      writer.close()
    }

  }

}
