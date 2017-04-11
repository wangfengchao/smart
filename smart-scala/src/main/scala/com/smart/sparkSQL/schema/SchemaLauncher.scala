package com.smart.sparkSQL.schema

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 定义case class Person 数据格式
  * Created by fc.w on 2017/4/11.
  */
object SchemaLauncher {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("sparkSQL_cache")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    // this is used to implicitly convert an RDD to a DataFrame.
    import sqlContext.implicits._
    // RDD转换成临时表
    val peopleDF = sc.textFile(args(0)).map(_.split(",")).map(p => Person(p(0), p(1).trim.toInt)).toDF()
    peopleDF.registerTempTable("people")
    // 获取分区数
    println("partitions size: "+ peopleDF.rdd.partitioner.size)
    //cache table
    sqlContext.cacheTable("people")

    val teenagers = sqlContext.sql("SELECT name, age FROM people")
    teenagers.show()

    /** DF 所有获取结果的方式 */
    // 1. 通过下标索引获取结果数据
    teenagers.map(row => "Name: " + row(0)).collect().foreach(println)
    // 2. by field name:
    teenagers.map(row => "Name: " + row.getAs("name")).collect().foreach(println)
    // 3. row.getValuesMap[T] retrieves multiple columns at once into a Map[String, T]
    teenagers.map(_.getValuesMap[Any](List("name", "age"))).collect().foreach(println)

  }

}
