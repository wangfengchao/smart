package com.smart.FPGrowth

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
  * Created by fc.w on 2017/6/21.
  */
object Test {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Test").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new HiveContext(sc)

    val result = sqlContext.sql("select guid, house_id from lifang.mid_user_house_action  where dt = '20170620'")
    val structData = result.map(row => (row.get(0).toString, row.get(1).toString)).aggregateByKey(mutable.HashSet[String](), 1)(seq,comb)
    structData.values.map(record => {
      val str = new StringBuilder()
      record.foreach(ele => {
        str ++= (ele + ",")
      })
      str.substring(0, str.length - 1).toString
    }).saveAsTextFile("/user/root/fpgData")

    val data = sc.textFile("/user/root/fpgData")

  }

  def seq = (houseIds: mutable.HashSet[String], houserId: String) => houseIds += houserId
  def comb = (curr: mutable.HashSet[String], next: mutable.HashSet[String]) => curr ++= next

}
