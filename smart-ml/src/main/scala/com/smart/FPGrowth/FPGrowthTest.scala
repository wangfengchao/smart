package com.smart.FPGrowth

import java.util.concurrent.ConcurrentHashMap

import com.smart.utils.SparkLearningFunSuite
import org.apache.spark.mllib.fpm.FPGrowth

import scala.collection.mutable

/**
  *  商品关联规则挖掘。
  *  版本一
  * Created by fc.w on 2017/6/21.
  */
class FPGrowthTest extends SparkLearningFunSuite {

  test(" beer and chicken test") {

    val values = mutable.ArrayBuffer[String]()
    values += "尿布"
    values += "啤酒"

    // 用户购买的历史商品
    val items = new ConcurrentHashMap[Int, mutable.ArrayBuffer[String]]()
    items.put(1, values)
    var li = mutable.ArrayBuffer[ItemFreq]()
    var freqMap = new ConcurrentHashMap[mutable.ArrayBuffer[String], mutable.ArrayBuffer[ItemFreq]]()

    val path = System.getProperty("user.dir")
    val data = sc.textFile(s"${path}/src/main/resources/data/fpgrowth.txt").map(_.trim.split(" ")).cache()

    val minSupport = 0.5
    val minConf = 0.3
    val numPartition = 1

    val fpg = new FPGrowth()
      .setMinSupport(minSupport)
      .setNumPartitions(numPartition)

    val model = fpg.run(data)

    // 2.输出所有频繁项集
    val freqItemSets = model.freqItemsets.filter(_.items.size >= 1).collect()
    freqItemSets.foreach(f => println(f.items.mkString(" ") + " -> " + f.freq ))

    // 3. 获取用户购买记录
    val bucket = items.get(1)
    // 4. 从频繁项集中找到与用户历史商品相符的项集，得到频次
    var freq = 0
    for (itemSet <- freqItemSets) {
      val i = itemSet.items
      if (i.contains("啤酒") && i.contains("尿布")) {
        freq = itemSet.freq.toInt
      }
    }

    println(s"历史商品出现次数:" + freq)
    for (f <- freqItemSets) {
      if (f.items.mkString.contains("啤酒") && f.items.mkString.contains("尿布") && f.items.size >= items.get(1).length) {
        val conf: Double = f.freq.toDouble / freq.toDouble
        if (conf >= minConf) {
          var item = f.items
          for (i <- 0 until items.get(1).length) {
            item = item.filter(_ != items.get(1)(i))
          }
          for (str <- item) {
            li += ItemFreq(str, conf)
          }
        }

      }
    }

    freqMap.put(items.get(1),li)
    println("推荐的商品为：")
    freqMap.get(items.get(1)).foreach(f =>println(f.item + "->" + f.freq))

  }

}

case class ItemFreq(val item: String, val freq: Double)
