package com.smart.spark.utils

import com.typesafe.config.ConfigFactory

/**
  * Created by fc.w on 2017/3/29.
  */
object CommonUtils {

  private val config = ConfigFactory.load()

  /** Spark */
  val appNameAsMysql = config.getString("app_mysql_name")
  val appNameAsZK    = config.getString("app_zk_name")
  val checkPointMysqlDir = config.getString("checkpoint_mysql_dir")
  val checkPointZKDir    = config.getString("checkpoint_zk_dir")

  /** Kafka */
  val groupId         = config.getString("kafka_group_id")
  val brokerList      = config.getString("kafka_broker_list")
  val kafkaBrokers    = config.getString("kafka_brokers")
  val kafkaMysqlGroup = config.getString("kafka_mysql_group")
  val kafkaMysqlTopic = config.getString("kafka_mysql_topic")
  val kafkaZKTopic    = config.getString("kafka_zk_topic")
  val kafkaZKGroup    = config.getString("kafka_zk_group")

  /** Zookeeper */
  val zkServer = config.getString("zkServer")

  /** Mysql */
  val driver    = config.getString("driver")
  val mysqlUrl  = config.getString("url")
  val userName  = config.getString("username")
  val password  = config.getString("password")
  val maxActive = config.getString("maxActive")
  val minIdle   = config.getString("minIdle")
  val maxIdle   = config.getString("maxIdle")
  val initSize  = config.getString("initSize")
  val maxWait   = config.getString("maxWait")
  val defaultAutoCommit = config.getString("defaultAutoCommit")
  val defaultReadOnly   = config.getString("defaultReadOnly")

  /** Email */
  val mail_enable = config.getString("mail_enable")
  val mail_host = config.getString("mail_host")
  val mail_port = config.getString("mail_port")
  val mail_auth = config.getString("mail_auth")
  val smtp_host = config.getString("smtp_host")
  val smtp_port = config.getString("smtp_port")
  val fm_email  = config.getString("fm_email")
  val to_email  = config.getString("to_email")
  val auth_code = config.getString("auth_code")
  val isAuth    = config.getString("isAuth")
  val isSSL     = config.getString("isSSL")
}
