package com.smart.spark.mapTopair_test

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fc.w on 2017/6/26.
  */
object MapToPairLauncher {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[1]").setAppName("MapToPair_Test")
    val sc = new SparkContext(conf)

    val lines = sc.textFile("/data/test.txt")
    val pairs = lines.map(line => (line.split(",")(0), 1))
  }

}
