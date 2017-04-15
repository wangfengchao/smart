package com.smart.spark.utils

import kafka.common.TopicAndPartition
import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.retry.RetryUntilElapsed
import org.apache.spark.streaming.kafka.OffsetRange
import org.codehaus.jackson.map.ObjectMapper
import scala.collection.JavaConversions._
import scala.collection.mutable

/**
  * Created by fc.w on 2017/3/30.
  */
object ZkUtils {

  /**
    *  获取 ZK 链接
    */
  def getzkCurator(zkServer: String): CuratorFramework = {
    CuratorFrameworkFactory.builder()
      .connectString(zkServer)
      .connectionTimeoutMs(1000)
      .sessionTimeoutMs(10000)
      .retryPolicy(new RetryUntilElapsed(1000, 1000))
      .build()
  }

  /**
    * kafka offset 保存到 ZK
    * @param groupId groupId
    * @param offsetRanges offsetRange
    */
  def kafkaOffset2Znode(groupId: String, offsetRanges: Array[OffsetRange]): Unit = {
    val curatorFramework = getzkCurator(CommonUtils.zkServer)
    val objectMapper = new ObjectMapper()
    try {
      curatorFramework.start()
      offsetRanges.foreach(offsetRange => {
        val offsetBytes = objectMapper.writeValueAsBytes(offsetRange.untilOffset)
        val nodePath = "/consumers/" + groupId + "/offsets/" + offsetRange.topic + "/" + offsetRange.partition
        if (null != curatorFramework.checkExists.forPath(nodePath)) {
          curatorFramework.setData().forPath(nodePath,offsetBytes)
          println("3.----- append znode path: " + nodePath)
        } else {
          curatorFramework.create().creatingParentsIfNeeded().forPath(nodePath, offsetBytes)
          println("3.----- create znode path: " + nodePath)
        }

      })
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      curatorFramework.close()
    }
  }

  /**
    * 从ZK中读取offset
    * @param zkServer ZK
    * @param groupId group
    * @param topic topic
    * @return
    */
  def getConsumerOffsets(zkServer: String, groupId: String, topic: String): mutable.HashMap[TopicAndPartition, Long] = {
    val retMap = mutable.HashMap[TopicAndPartition, Long]()
    val objectMapper = new ObjectMapper()
    val curatorFramework = getzkCurator(zkServer)
    try {
      curatorFramework.start()
      val nodePath = "/consumers/" + groupId + "/offsets/" + topic
      if (null != curatorFramework.checkExists().forPath(nodePath)) {
        val partitionList = curatorFramework.getChildren.forPath(nodePath)
        partitionList.foreach(partition => {
          val offset = objectMapper.readValue(curatorFramework.getData.forPath(nodePath + "/" + partition), classOf[Long])
          val topicAndPartition = TopicAndPartition(topic, partition.toInt)
          println("2. -------- topicAndPartition: " + topicAndPartition + "   offsets: " + offset)
          retMap += (topicAndPartition -> offset)
        })
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      curatorFramework.close()
    }

    retMap
  }

}
