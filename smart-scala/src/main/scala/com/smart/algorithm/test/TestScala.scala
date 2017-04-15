package com.smart.algorithm.test

import scala.collection.mutable


/**
  * Created by fc.w on 2017/3/7.
  */
object TestScala {

  def main(args: Array[String]): Unit = {
    val map = mutable.HashMap[String, String]()
    map += ("a" -> "b")
    map -= ("a")
  }
}
