package com.smart.kmeans

import java.io.{File, PrintWriter}

import org.apache.spark.ml.feature.MinMaxScaler
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.{DenseVector, Vectors}
import org.apache.spark.sql.hive.HiveContext

/**
  * 房源画像 之 热搜房源
  * Created by fc.w on 2017/9/13.
  */
object House_Kmeans {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("House_Kmeans-Launcher")
    val sc = new SparkContext(conf)
    val sqlContext = new HiveContext(sc)

    /* 数据归一化 */
    val houseDF = sqlContext.sql(s"select house_id, pv, dwell_time, coll_count  from wkdw.dw_m_bp_merge_house where city_id = 43 and house_id != '' and dwell_time <= 86400")
    val vectorRDD = houseDF.map(row => {(row.getString(0).toLong, Vectors.dense(row.getInt(1),row.getDouble(2), row.getInt(3))) })
    val dataFrame = sqlContext.createDataFrame(vectorRDD).toDF("house_id", "features")
    val scaler = new MinMaxScaler().setInputCol("features").setOutputCol("scaledFeatures")
    val scalerModel = scaler.fit(dataFrame)
    val scaledData = scalerModel.transform(dataFrame)
    println(s"Features scaled to range: [${scaler.getMin}, ${scaler.getMax}]")
    val parsedData = scaledData.select("house_id", "features", "scaledFeatures").map(row => {
      val vectors = row.get(2).asInstanceOf[DenseVector].toArray
      (row.get(0), row.get(1), Vectors.dense(vectors))
    })

    /* K-means */
    val numClusters = 3
    var numIterations = 2
    val runs = 2
    val initMode = "k-means||"
    for (j <- 0 until 10) {
      numIterations += 2
      val writer = new PrintWriter(new File(s"/data/kmeans/data_k${numClusters}_${numIterations}.txt"))
      writer.println(s"===============numClusters = ${numClusters}============= numIterations = ${numIterations} ============== runs = ${runs}=========================================")
      val clusters = new KMeans()
        .setInitializationMode(initMode)
        .setK(numClusters)
        .setMaxIterations(numIterations)
        .run(parsedData.map(_._3))
      writer.println(parsedData.map(v=> s"houseId: ${v._1}   features:${v._2}  normalization：${v._3}   belong to cluster :" + clusters.predict(v._3)).collect().mkString("\n"))

      // 误差平方和
      val WSSSE = clusters.computeCost(parsedData.map(_._3))
      writer.println(s"WithinSet Sum of Squared Errors = ${WSSSE}")
      println(s"WithinSet Sum of Squared Errors = ${WSSSE}")
      //统计每个类簇出现的次数
      val res = parsedData.map(vector => (clusters.predict(vector._3), 1))
        .reduceByKey((a, b) => (a + b))
        .map(f => f._2)
        .collect()
      //打印出每列特征值的中心点
      println("Clustercenters:")
      var i = 0
      val count = clusters.clusterCenters(0).size
      for (center <- clusters.clusterCenters) {
        val pv = BigDecimal(center(0)).setScale(7, BigDecimal.RoundingMode.HALF_UP)
        val dwell_time = BigDecimal(center(1)).setScale(7, BigDecimal.RoundingMode.HALF_UP)
        val coll_count = BigDecimal(center(2)).setScale(4, BigDecimal.RoundingMode.HALF_UP)
        writer.println(s"\tFeatures Center ${count}:\tpv = ${pv}\tdwell_time = ${dwell_time}\tcoll_count = ${coll_count}\t    Cluster times: ${res(i)}")
        println(s"\tCenter point:\t${center}")
        i += 1
      }

      writer.close()
    }

  }

}
