package com.smart.kafka

import scala.util.Random

/**
  * Created by fc.w on 2017/4/17.
  */
object KafkaProducer {

  private val users = Array("zhangsan", "lisi", "wangwu", "zhaoliu")
  private val random = new Random()
  def payMount(): Double = random.nextInt(10)
  def getUserName(): String = users(random.nextInt(users.length))

  

}
