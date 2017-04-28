package com.smart.utils

import com.typesafe.config.ConfigFactory
import kafka.serializer.StringEncoder

/**
  * Created by fc.w on 2017/4/17.
  */
object CommonUtils {

  private val config = ConfigFactory.load()

  /** Spark */
  val appName = config.getString("appName")
  val checkPoint = config.getString("check_point_dir")

  /** Kafka */
  val kafkaBrokerList      = config.getString("kafka_broker_list")
  val kafkaGroupParam      = config.getString("kafka_group_param")
  val kafkaBrokerListParam = config.getString("kafka_broker_list_param")
  val kafkaTopic = config.getString("kafka_topic")
  val kafkaGroup = config.getString("kafka_group")
  val kafkaParams = Map[String, String](
    CommonUtils.kafkaBrokerListParam -> CommonUtils.kafkaBrokerList,
    CommonUtils.kafkaGroupParam -> CommonUtils.kafkaGroup
  )
}
