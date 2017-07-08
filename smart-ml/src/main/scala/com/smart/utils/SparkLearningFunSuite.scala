package com.smart.utils

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfterAll, FunSuite, Suite}

/**
  * 定义生成spark环境和FunSuite测试类
  * Created by fc.w on 2017/6/21.
  */
class SparkLearningFunSuite extends FunSuite with BeforeAndAfterAll {

  self: Suite =>
  @transient var sc: SparkContext = _
  @transient var sqlContext: SQLContext = _
  Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
  Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.ERROR)

  override protected def beforeAll(): Unit = {
    super.beforeAll()

    val conf = new SparkConf().setAppName("SparkLearningTest").setMaster("local[2]")
    sc = new SparkContext(conf)
    sqlContext = new SQLContext(sc)
  }

  override protected def afterAll(): Unit = {
    sqlContext = null
    if (sc != null) sc.stop()
    sc = null

    super.afterAll()
  }

}
