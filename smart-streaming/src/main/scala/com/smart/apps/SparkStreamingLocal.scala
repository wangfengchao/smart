package com.smart.apps

import java.text.SimpleDateFormat
import java.util.Date

import com.alibaba.fastjson.JSON
import com.smart.apps.utils.{HbaseHandler, RedisTools}
import kafka.serializer.StringDecoder
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 在本地执行
  * 方便测试
  * Created by fc.w on 2017/6/15.
  */
object SparkStreamingLocal {

  var date = 4

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SpringStreaming_Local_Launcher").setMaster("local[1]")
    val ssc = new StreamingContext(conf, Seconds(30))
    ssc.checkpoint("/user/checkpoint/risk")
    val kafkaParams = Map[String, String] (
      "metadata.broker.list" -> "h5:9092,h6:9092,h7:9092",
      "group.id" -> "spark_local"
    )
    val topics = Set[String]("test_pv")

    val lines = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)

    lines.map(_._2).print()

    // 数据处理
    val dataRDD = lines.map(lineJSON => {
      val obj = JSON.parseObject(lineJSON._2)
      val ip = obj.getString("ip")
      var userAgent = ""
      if (obj.containsKey("user_agent")) {
        userAgent = obj.getString("user_agent")
      }
      (ip, userAgent)
    }).filter(record => (!RiskUtils.userAgentMap.contains(record._2)))
      .map(record => (record._1, 1))

//    /* 每隔一小时统计最近一小时相同IP出现的次数 过滤IP数小于120 */
//    val hourRDD = dataRDD.reduceByKeyAndWindow(
//      (a: Int, b: Int) => a + b,
//      Seconds(60),
//      Seconds(60)
//    ).filter(_._2 >= 3)
//
//    hourRDD.foreachRDD(re => {
//      re.collect().foreach(c => println("-------- " + c))
//    })

    // 保存数据
//    hourRDD.foreachRDD(saveHBaseRedis(_, "hour"))


    // 统计每天IP,过滤IP数小于500
    val dayRDD = dataRDD.reduceByKey(_+_)
      .updateStateByKey[Int](updateFunc)
      .filter(_._2 >= 6)

    dayRDD.foreachRDD(re => {
      re.collect().foreach(c => println("-------- " + c))
    })
//
//
//    dayRDD.foreachRDD(rdd => {
//      rdd.foreach(println("------------------------------dayRDD  ip: ", _))
//    })


    ssc.start()
    ssc.awaitTermination()
  }

  var currDate = 0
  def updateFunc = (currValues : Seq[Int], preVauleState : Option[Int]) => {
    val currSum = currValues.sum
    val previousSum = preVauleState.getOrElse(1)
//    val currDate = getNowDate
    val count = currSum + previousSum
    currDate += 1
    println(s"date: ${date}     currDate: ${currDate}   count: ${count}")
    if (date != currDate) {
      Some(count)
    } else {
      currDate = 0
      None
    }
  }

  def getNowDate():String={
    val now = new Date()
    var dateFormat:SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00")
    var hehe = dateFormat.format(now)
    hehe
  }

//  def saveHBaseRedis(ipTimesRDD: RDD[(String, Int)], flag: String): Unit = {
//    ipTimesRDD.foreachPartition(ipTimesPra => {
//      val conn = HbaseHandler.getConn()
//      ipTimesPra.foreach(ipTimes => {
//        val table = conn.getTable(TableName.valueOf("risk_control"))
//        try {
//          /* redis */
//          val jedis = RedisTools.getRedis
//          val ip = ipTimes._1
//          val count = ipTimes._2.toString
//          val key = "risk_control_ip_" + ip
//          jedis.set(key, count)
//          // key 失效时间设置7天
//          jedis.expire(key, (24 * 60 * 60))
//
//          // rowKey = 时间戳 + ip
//          val rowKey = new Date().getTime + ipTimes._1
//          val put = new Put(Bytes.toBytes(rowKey))
//          put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes(flag), Bytes.toBytes(ipTimes._1))
//          table.put(put)
//
//        } catch {
//          case e: Exception => e.printStackTrace()
//        } finally {
//          table.close()
//        }
//
//      })
//
//      conn.close()
//    })
//  }



}
