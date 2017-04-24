package com.smart.kafka

import java.util.Properties

import com.alibaba.fastjson.JSONObject
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import scala.util.Random

/**
  * kafka producer
  * Created by fc.w on 2017/4/17.
  */
object KafkaProducer_Test extends App{

  private val users = Array("zhangsan", "lisi", "wangwu", "zhaoliu")
  private val random = new Random()
  def payMount(): Double = random.nextInt(10)
  def getUserName(): String = users(random.nextInt(users.length))

  val properties = new Properties
  properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "h5:9092,h6:9092,h7:9092")
  properties.put(ProducerConfig.CLIENT_ID_CONFIG, "MsgProducer")
  properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](properties)
  while(true) {
    // 创建json串
    val event = new JSONObject()
    event.put("user", getUserName)
    event.put("payment", payMount)

    // 往kafka发送数据
    producer.send(new ProducerRecord[String, String]("recommend_group_topic", event.toJSONString))
    println("Message sent: " + event)

    //每隔200ms发送一条数据
    Thread.sleep(1000)
  }
}
