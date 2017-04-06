package com.smart.spark.utils

import kafka.api.{OffsetRequest, PartitionOffsetRequestInfo}
import kafka.common.TopicAndPartition
import kafka.javaapi.TopicMetadataRequest
import kafka.javaapi.consumer.SimpleConsumer
import scala.collection.JavaConversions._
import scala.collection.mutable

/**
  * Created by fc.w on 2017/3/30.
  */
object KafkaUtil {

  /**
    * 从kafka中读取offset
    * @param kafkaBrokers Kafka
    * @param topic topic
    * @return
    */
  def getKafkaTopicOffsets(kafkaBrokers: String, topic: String):  mutable.HashMap[TopicAndPartition, Long] = {
    val retMap = mutable.HashMap[TopicAndPartition, Long]()
    kafkaBrokers.split(",").foreach(broker => {
      val simpleConsumer = new SimpleConsumer(broker.split(":")(0), broker.split(":")(1).toInt, 10000, 1024, "consumer")
      val topicMetadataRequest  = new TopicMetadataRequest(List(topic))
      val topicMetadataResponse  = simpleConsumer.send(topicMetadataRequest)

      try {
        topicMetadataResponse.topicsMetadata.foreach(_.partitionsMetadata.foreach(partitionMetadata => {
          val leader = partitionMetadata.leader
          if (null != leader) {
            val topicAndPartition = TopicAndPartition(topic, partitionMetadata.partitionId)
            val partitionOffsetRequestInfo = PartitionOffsetRequestInfo(OffsetRequest.LatestTime, 10000)
            val requestInfo = new java.util.HashMap[TopicAndPartition, PartitionOffsetRequestInfo]()
            requestInfo.put(topicAndPartition, partitionOffsetRequestInfo)
            val offsetRequest = new kafka.javaapi.OffsetRequest(requestInfo, OffsetRequest.CurrentVersion, simpleConsumer.clientId)
            val offsetResponse = simpleConsumer.getOffsetsBefore(offsetRequest)
            if (!offsetResponse.hasError) {
              val offsets = offsetResponse.offsets(topic, partitionMetadata.partitionId)
              println("1.------ getKafkaTopicOffsets: " + topicAndPartition + "   offsets: " + offsets(0))
              retMap += (topicAndPartition -> offsets(0))
            }
          }
        }))
      } catch {
        case e: Exception => e.printStackTrace()
      } finally {
        simpleConsumer.close()
      }
    })

    retMap
  }



}
