package com.smart.apps

import com.smart.model.DataModel.BuryingPointModel
import com.smart.service.BuryingPointForewarningService
import com.smart.utils.CommonUtils
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

/**
  * Created by fc.w on 2017/4/28.
  */
object Test {
  def main(args: Array[String]): Unit = {
    val ssc = StreamingContext.getOrCreate(CommonUtils.checkPoint, createContextFunc)

    ssc.start()
    ssc.awaitTermination()
  }

  /**
    * 逻辑处理主函数
    * @return
    */
  def createContextFunc(): StreamingContext = {
    val conf = new SparkConf().setAppName(CommonUtils.appName)
    val ssc = new StreamingContext(conf, Seconds(60))
    ssc.checkpoint(CommonUtils.checkPoint)
    val topics = Set(CommonUtils.kafkaTopic)
    val rddDStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, CommonUtils.kafkaParams, topics)
    val record = rddDStream.mapPartitions(BuryingPointForewarningService.parseRecordFunc).cache()
    // 保存参数名称
    val modelTupleDStream = record.mapPartitions(modelPartition => {
      val result = mutable.HashMap[BuryingPointModel, Int]()
      modelPartition.foreach(model => result += (model -> 1))
      result.toIterator
    })

    ssc
  }
}
