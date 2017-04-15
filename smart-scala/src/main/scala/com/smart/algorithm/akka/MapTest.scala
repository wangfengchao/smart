package com.smart.algorithm.akka

import scala.collection.mutable

/**
  * Created by fc.w on 2017/3/8.
  */
object MapTest {

  def main(args: Array[String]): Unit = {
    var aa ,bb  , cc = "1"
    val map = mutable.HashMap[String, String]()
    map += ("a" -> "b")
    println(map.getOrElse("b", "c"))
    println(aa)
  }
}
