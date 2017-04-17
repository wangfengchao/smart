package com.lifang

import com.lifang.util.CommonUtils
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by fc.w on 2017/4/12.
  */
object Launcher {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(CommonUtils.appName)
    val ssc = new StreamingContext(conf, Seconds(60))
    val topics = Set(CommonUtils.kafkaTopic)
    val kafkaParams = Map[String, String](
      CommonUtils.kafkaBrokerListParam -> CommonUtils.kafkaBrokerList,
      CommonUtils.kafkaGroupParam -> CommonUtils.kafkaGroup
    )

    val kafkaStream =  KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)

    kafkaStream.map(_._2).countByValue().foreachRDD(re => re.foreach(record => println("countByValue: " + record)))

   kafkaStream.map(_._2.split(",")).flatMap(x => {
      for (i <- 0 until x.length - 1) yield (x(i) + "," + x(i + 1), 1)
    }).reduceByKey(_+_).foreachRDD(record => {
      record.foreach(record => println("flatMap + recudeByKey: " + record))
    })

    kafkaStream.map(_._2).foreachRDD(rdd => {
      rdd.distinct().foreach(record => println("distinct: " + record))
    })


    ssc.start()
    ssc.awaitTermination()
  }

}
