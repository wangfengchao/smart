package com.smart.scala

import scala.collection.mutable.{ArrayBuffer, HashMap, HashSet}

/**
  * Created by fc.w on 2017/6/29.
  */
object HashSet_Test {

  def main(args: Array[String]): Unit = {
    val drivers = new HashSet[String]
    drivers.find(_ == "aa") match {
      case Some(str) =>
        drivers -= "aa"
      case None =>

    }


    val bbb = new HashMap[String, String]
    bbb.find(record => record._1 == "aa") match {
      case Some(rec) =>
        rec._2 +" "+ rec._1
    }

  }

}

