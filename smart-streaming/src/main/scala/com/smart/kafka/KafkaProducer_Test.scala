package com.smart.kafka

import java.text.SimpleDateFormat
import java.util.{Date, Properties}

import com.alibaba.fastjson.JSONObject
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import scala.util.Random

/**
  * kafka producer
  * Created by fc.w on 2017/4/17.
  */
object KafkaProducer_Test extends App{

  private val project_id = Array("wkzf", "yfyk")
  private val os_plant = Array("ios", "android")
  private val random = new Random()
  def projectId(): String = project_id(random.nextInt(project_id.length))
  def osPlant(): String = os_plant(random.nextInt(os_plant.length))

  val properties = new Properties
  properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "h5:9092,h6:9092,h7:9092")
  properties.put(ProducerConfig.CLIENT_ID_CONFIG, "MsgProducer")
  properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](properties)
  while(true) {
    // 创建json串
    val event = new JSONObject()
    event.put("project_id", "project3")
    event.put("os_plant", "oplant3")
    event.put("page_name", "2000")
    event.put("type", "1")
    event.put("page_param", "{\"HouseBelong\":\"0\",\"HouseID\":\"2340623\",\"Boutique\":\"1\"}")

    // 往kafka发送数据
    producer.send(new ProducerRecord[String, String]("recommend_group_topic", event.toString))
    println("Message sent: " + event.toString   +"     date :"+ dataa )

    //每隔200ms发送一条数据
    Thread.sleep(10000)
  }


  def dataa (): String = {
    val date = new Date()
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    dateFormat.format(date)
  }
}
