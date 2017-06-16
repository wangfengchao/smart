package com.smart.apps

import kafka.serializer.StringDecoder
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf}

/**
  * 在本地执行
  * 方便测试
  * Created by fc.w on 2017/6/15.
  */
object SparkStreamingLocal {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SpringStreaming_Local_Launcher").setMaster("local[1]")
    val ssc = new StreamingContext(conf, Seconds(10))
    val kafkaParams = Map[String, String] (
      "metadata.broker.list" -> "h5:9092,h6:9092,h7:9092",
      "group.id" -> "spark_local"
    )
    val topics = Set[String]("pv")

    val lines = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)
    lines.map(_._2).print()

    ssc.start()
    ssc.awaitTermination()
  }

}
