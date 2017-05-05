package com.smart.scala

import scala.collection.mutable.ListBuffer


/**
  * Created by fc.w on 2017/5/2.
  */
object UnzipTest {

  def main(args: Array[String]): Unit = {
    val list = ListBuffer[(Int, Int)]()
    list += ((1, 10))
    list += ((2, 20))

    val (t1, t2) = list.unzip
    println(t1 +"  "+ t2)

  }

}
