package com.smart.spark

import java.text.SimpleDateFormat
import java.util.Date

import com.smart.spark.utils.{CommonUtils, DBUtils, KafkaUtil}
import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by fc.w on 2017/3/29.
  */
object KafkaOffsetLauncher {

  def main(args: Array[String]): Unit = {
    val ssc = StreamingContext.getOrCreate(CommonUtils.checkPointMysqlDir ,functionToCreateContext)
    ssc.start()
    ssc.awaitTermination()
  }

  /**
    * SparkStreaming Func
    * @return
    */
  def functionToCreateContext(): StreamingContext = {
    val conf = new SparkConf().setAppName(CommonUtils.appNameAsMysql)
    val ssc = new StreamingContext(conf, Seconds(20))
    ssc.checkpoint(CommonUtils.checkPointMysqlDir)
    val kafkaParams = Map[String, String](
      CommonUtils.brokerList -> CommonUtils.kafkaBrokers,
      CommonUtils.groupId -> CommonUtils.kafkaMysqlGroup
    )
    val topicOffset = KafkaUtil.getKafkaTopicOffsets(CommonUtils.kafkaBrokers, CommonUtils.kafkaMysqlTopic)
    val consumerOffset = getKafkaConsumerOffsets()
    topicOffset ++= consumerOffset
    val messageHandler = (mam: MessageAndMetadata[String, String]) => (mam.topic, mam.message())
    val stream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder,(String, String)](ssc, kafkaParams, topicOffset.toMap, messageHandler)
    var offsetRanges = Array[OffsetRange]()
    stream.transform(rdd => {
      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      rdd
    }).mapPartitions(records => {
      val words = ListBuffer[(String, Int)]()
      records.foreach(_._2.split(",").foreach(aa => words += ((aa, 1))))
      words.iterator
    }).reduceByKey(_+_)
    .foreachRDD(rdd => {
      kafkaOffset2Mysql(offsetRanges)
      rdd.foreach(re => println(re._1 + "    " + re._2))
    })

    ssc
  }

  /**
    * 获取kafka偏移量
    * @return
    */
  def getKafkaConsumerOffsets(): mutable.HashMap[TopicAndPartition, Long] = {
    val retMap = mutable.HashMap[TopicAndPartition, Long]()
    val conn = DBUtils.getConnection()
    try {
      val stmt = conn.createStatement()
      val resultSet = stmt.executeQuery("select kafka_partition, kafka_offset from  kafka_offsets where kafka_topic = \"topic_mysql_offset\"")
      while (resultSet.next()) {
        val topicAndPartition = TopicAndPartition(CommonUtils.kafkaMysqlTopic,  resultSet.getInt(1))
        println(s"2. ----------kafka_partition: ${ resultSet.getInt(1)}   kafka_offset: ${resultSet.getInt(2)}")
        retMap += (topicAndPartition -> resultSet.getInt(2))
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      DBUtils.closeConnection()
    }

    retMap
  }


  /**
    * Save KafkaOffset into Mysql
    * @param offsetRanges
    */
  def kafkaOffset2Mysql(offsetRanges: Array[OffsetRange]): Unit = {
    try {
      println("----------kafkaOffset2Mysql--------------")
      val conn = DBUtils.getConnection()
      val stmt = conn.createStatement
      val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      val date = new Date()
      for (o <- offsetRanges) {
        println(s"1.-------topic: ${o.topic}  partition: ${o.partition}   fromOffset: ${o.fromOffset}    untilOffset: ${o.untilOffset}")
        stmt.executeUpdate("update kafka_offsets set kafka_offset = " + o.untilOffset + ", dt = \""+ sdf.format(date) +"\" where kafka_topic = \"" + o.topic + "\" and kafka_partition = " + o.partition)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      DBUtils.closeConnection()
    }
  }

}
