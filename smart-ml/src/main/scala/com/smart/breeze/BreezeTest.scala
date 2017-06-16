package com.smart.breeze

import breeze.linalg.{DenseMatrix, DenseVector}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Breeze函数库
  * Created by fc.w on 2017/6/16.
  */
object BreezeTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("breeze_test").setMaster("local[1]")
    val sc = new SparkContext(conf)
    Logger.getRootLogger.setLevel(Level.WARN)

    // 3.1.1 Breeze 创建函数
    val m1 = DenseMatrix.zeros[Double](2, 3)
    println(s"DenseMatrix.zeros[Double](2, 3)初始化一个2行3列的矩阵：\n ${m1}")

    val v1 = DenseVector.zeros[Double](3)
    println(s"\nDenseVector.zeros[Double](3) 初始值为0，长度为3的向量：\n ${v1}")

    val v2 = DenseVector.ones[Double](3)
    println(s"\nDenseVector.ones[Double](3) 初始为1，长度为3的向量：\n${v2}")

    val v3 = DenseVector.fill[Double](3){5.0}
    println(s"\nDenseVector.fill[Double](3){5.0}")
  }

}
