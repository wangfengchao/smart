package com.smart.spark.sort_second

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 二次排序测试
  * Created by fc.w on 2017/6/26.
  */
object SecondarySortLauncher {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[1]").setAppName("SecondarySortLauncher")
    val sc = new SparkContext(conf)

    val path = System.getProperty("user.dir")
    val lines = sc.textFile(s"${path}/smart-scala/src/main/resources/data/sort.txt")
    val pairs = lines.map(line => (new Secondary_SortKey(line.split(",")(0).toInt, line.split(",")(1).toInt), line))
    val sortedPairs = pairs.sortByKey()
    val sortedLines = sortedPairs.map(_._2)
    sortedLines.foreach(println)

  }

}
