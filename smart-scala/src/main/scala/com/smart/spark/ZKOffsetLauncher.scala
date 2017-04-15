package com.smart.spark

import com.smart.spark.utils.{CommonUtils, KafkaUtil, ZkUtils}
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer

/**
  * Created by fc.w on 2017/3/30.
  */
object ZKOffsetLauncher {

  def main(args: Array[String]): Unit = {
    val ssc = StreamingContext.getOrCreate(CommonUtils.checkPointZKDir, functionToCreateContext)
    ssc.start()
    ssc.awaitTermination()
  }

  /**
    * Create SparkStreaming Func
    * @return
    */
  def functionToCreateContext(): StreamingContext = {
    val conf = new SparkConf().setAppName(CommonUtils.appNameAsZK)
    val ssc = new StreamingContext(conf, Seconds(20))
    try {
      ssc.checkpoint(CommonUtils.checkPointZKDir)
      val kafkaParams = Map(
        CommonUtils.brokerList -> CommonUtils.kafkaBrokers,
        CommonUtils.groupId -> CommonUtils.kafkaZKGroup
      )
      val topicOffset = KafkaUtil.getKafkaTopicOffsets(CommonUtils.kafkaBrokers, CommonUtils.kafkaZKTopic)
      val consumerOffsets = ZkUtils.getConsumerOffsets(CommonUtils.zkServer, CommonUtils.kafkaZKGroup, CommonUtils.kafkaZKTopic)
      topicOffset ++= consumerOffsets
      val messageHandler = (mam: MessageAndMetadata[String, String]) => (mam.topic, mam.message())
      val kafkaDStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (String, String)](ssc, kafkaParams, topicOffset.toMap, messageHandler)
      var offsetRanges = Array[OffsetRange]()
      kafkaDStream.transform(rdd => {
        offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
        rdd
      }).mapPartitions(records => {
        val words = ListBuffer[(String, Int)]()
        records.foreach(_._2.split(",").foreach(aa => words += ((aa, 1))))
        words.iterator
      }).reduceByKey(_+_)
      .foreachRDD(record => {
        ZkUtils.kafkaOffset2Znode(CommonUtils.kafkaZKGroup, offsetRanges)
        record.foreach(ele => println(ele._1 + " ----> " + ele._2))
      })

    } catch {
      case e: Exception => e.printStackTrace()
    }
    ssc
  }

}
