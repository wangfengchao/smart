package com.smart.scala

import scala.collection.mutable

/**
  * Created by fc.w on 2017/6/21.
  */
object IterTest {

  def main(args: Array[String]): Unit = {
    val summaries: mutable.Map[String, Int] = mutable.Map.empty
    summaries.getOrElseUpdate("a", 1)
    summaries.getOrElseUpdate("b", 1)
    summaries.iterator.flatMap { case(item, summary) =>
      Iterator.single((item, summary)) ++ (item)
    }

    println(summaries.get("a"))
  }

}
