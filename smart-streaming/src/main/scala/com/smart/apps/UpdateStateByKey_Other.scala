package com.smart.apps

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by fc.w on 2017/8/11.
  */
object UpdateStateByKey_Other {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("StatefulNetworkWordCount").setMaster("local[3]")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    ssc.checkpoint(".")

    val lines = ssc.socketTextStream("192.168.26.140", 9999)
    val words = lines.flatMap(_.split(" "))
    val wordDstream = words.map(x => (x, 1))

    val initialRDD = ssc.sparkContext.parallelize(List(("hello", 1), ("world", 1)))

    val stateDstream = wordDstream.updateStateByKey[Int](updateFunc, new HashPartitioner(1), initialRDD)

    ssc.start()
    ssc.awaitTermination()
  }

  ///newUpdateFunc的返回值要求是iterator[(String,Int)]类型的
  def newUpdateFunc = (iterator: Iterator[(String, Seq[Int], Option[Int])]) => {
    iterator.flatMap(t => updateFunc(t._2, t._3))
  }

  def updateFunc = (values: Seq[Int], state: Option[Int]) => {
    val currentCount = values.sum
    val previousCount = state.getOrElse(0)
    Some(currentCount + previousCount)
  }




}
