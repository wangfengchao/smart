package com.smart.kafka

import com.alibaba.fastjson.JSON


/**
  * Created by fc.w on 2017/8/11.
  */
object Test {

  def main(args: Array[String]): Unit = {
    println(updateFunc(1000))

  }

  def updateFunc = (a: Int) => {
    if (a > 100) {
      None
    } else {
      Some(20)
    }

  }

}
