package com.smart.algorithm.akka

import com.typesafe.config.ConfigFactory


/**
  * Created by fc.w on 2017/3/7.
  */
object AkkaUtils {
  val config = ConfigFactory.load()
  def main(args: Array[String]): Unit = {
    val url = config.getString("url")
    print(url)
  }

}
