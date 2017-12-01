package com.smart.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fc.w on 2017/10/13.
  */
object WordTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    val sc = new SparkContext(conf)
    val data = sc.textFile("")
    val a = data.flatMap(_.split(",")).map(str => (str, 1)).reduceByKey(_+_)

  }

}
