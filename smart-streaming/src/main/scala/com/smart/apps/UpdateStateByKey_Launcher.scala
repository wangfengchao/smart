package com.smart.apps

import com.alibaba.fastjson.JSON
import com.smart.utils.CommonUtils
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * http://blog.csdn.net/lsshlsw/article/details/48298929
  * 餐厅老板想要统计每个用户来他的店里总共消费了多少金额，我们可以使用updateStateByKey来实现
  * Created by fc.w on 2017/4/17.
  */
object UpdateStateByKey_Launcher {

  def main(args: Array[String]): Unit = {
    val ssc = StreamingContext.getOrCreate(CommonUtils.checkPoint, functionToCreateContext)

    ssc.start()
    ssc.awaitTermination()
  }

  /**
    * main
    * @return
    */
  def functionToCreateContext(): StreamingContext = {
    val conf = new SparkConf().setAppName(CommonUtils.appName)
    val ssc = new StreamingContext(conf, Seconds(60))
    ssc.checkpoint(CommonUtils.checkPoint)
    val topics = Set(CommonUtils.kafkaTopic)
    val kafkaParams = Map[String, String](
      CommonUtils.kafkaBrokerListParam -> CommonUtils.kafkaBrokerList,
      CommonUtils.kafkaGroupParam -> CommonUtils.kafkaGroup
    )

    val recordStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)

    val paymentSum = recordStream.map(record => {
      val objJson = JSON.parseObject(record._2)
      (objJson.getString("user"), objJson.getIntValue("payment"))
    }).reduceByKey(_ + _)


    val totalPayment = paymentSum.updateStateByKey[Int](updateFunc)

    ssc
  }

  //将以前的数据和最新一分钟的数据进行求和
  def updateFunc = (currValues : Seq[Int],preVauleState : Option[Int]) => {
    val currSum = currValues.sum
    val previousSum = preVauleState.getOrElse(1)
    Some(currSum + previousSum)
  }


}
