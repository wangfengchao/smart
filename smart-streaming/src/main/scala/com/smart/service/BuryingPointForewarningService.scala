package com.smart.service

import com.alibaba.fastjson.JSON
import com.smart.dao.BuryingPointForewarningDao
import com.smart.model.DataModel.BuryingPointModel
import com.smart.utils.DateUtils
import org.apache.commons.lang.StringUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.kafka.OffsetRange

import scala.collection.JavaConversions._
import scala.collection.mutable

/**
  * 业务逻辑处理
  * Created by fc.w on 2017/4/25.
  */
object BuryingPointForewarningService {

  /**
    * 解析json
    * @param recordPartition
    * @return
    */
  def parseRecordFunc(recordPartition: Iterator[(String, String)]): Iterator[BuryingPointModel] = {
    val results = mutable.HashSet[BuryingPointModel]()
    recordPartition.foreach(record => {
      try {
        val jsonObj = JSON.parseObject(record._2)
        val productType = if (jsonObj.containsKey("project_id")) jsonObj.getString("project_id") else ""
        val osPlant     = if (jsonObj.containsKey("os_plant")) jsonObj.getString("os_plant") else jsonObj.getString("pch5flag")
        val params      = if (jsonObj.containsKey("page_param")) jsonObj.getString("page_param") else jsonObj.getString("event_param")
        val types       = jsonObj.getString("type")
        var nameId = ""
        var nameType = ""
        types match {
          case "1" => {
            nameId   = if (jsonObj.containsKey("page_name")) jsonObj.getString("page_name") else ""
            nameType = "page"
          }
          case "2" => {
            nameId   = if (jsonObj.containsKey("event_func")) jsonObj.getString("event_func") else ""
            nameType = "click"
          }
        }

        /* 解析params 拼接key */
        var paramNames = ""
        if (!StringUtils.isEmpty(params)) {
          val sb = new StringBuilder()
          JSON.parseObject(params).keySet().foreach(paramName => sb.append(paramName + ","))
          // 去掉最后一个逗号
          paramNames = sb.toString()
          paramNames = paramNames.substring(0, paramNames.length - 1)
        }

        println(s"productType:${productType}    osPlant:${osPlant}  nameId：${nameId}   nameType: ${nameType}    paramNames:${paramNames}")
        results += new BuryingPointModel(productType, osPlant, paramNames, nameId, nameType)
      } catch {
        case e: Exception => e.printStackTrace()
      }
    })

    results.toIterator
  }

  /**
    * 把记录转换为键值对
    * @param modelPartition
    * @return
    */
  def model2Tuple(modelPartition: Iterator[BuryingPointModel]): Iterator[(BuryingPointModel, Int)] = {
    val result = mutable.HashMap[BuryingPointModel, Int]()
    modelPartition.foreach(model => result += (model -> 1))
    result.toIterator
  }


  /**
    * 保存参数名称
    * @param rdd
    */
  def saveParamName(rdd: RDD[(BuryingPointModel, Int)]): Unit = {
    println("-----------into saveParamName -----------------")
    rdd.map(record => {
      println(s"${record._1.productType}     ${record._1.osPlant}     ${record._1.nameType}     ${record._1.nameId}      count: ${record._2}")
      record._1
    })
      .distinct()
      .foreachPartition(modelPartition => {
      modelPartition.foreach(BuryingPointForewarningDao.saveParamName)
    })
  }

  /**
    * 监控每个topic是否上报数据
    * @param rdd
    */
  def saveKafkaOffsetOrEmailWarning(rdd: RDD[BuryingPointModel], offsetRanges: Array[OffsetRange]): Unit = {
    println("-----------into saveKafkaOffsetOrEmailWarning -----------------")
    rdd.map(model => (model.productType, model.osPlant)).distinct()
      .foreachPartition(modelPartition => {
        /* jedis添加数据 */
        val jedis = RedisUtils.getRedis
        modelPartition.foreach(model => {
          val key = RedisPrefix.getKey(model._1, model._2)
          jedis.set(key, DateUtils.currentDate())
        })
      })

    // 保存偏移量
    BuryingPointForewarningDao.updateKafkaOffset(offsetRanges)

    /* 监测,添加异常邮件信息 */
    try {
      val jedis = RedisUtils.getRedis
      val keys = jedis.keys(RedisPrefix.REDIS_KEY_PREFIX + "*")
      val currentDate = DateUtils.currentDate()
      keys.foreach(key => {
        println(s"redis key: ${key}")
        val date = jedis.get(key)
        val minutes = DateUtils.timeDifferenceMinutes(currentDate, date)
        if (minutes > SparkUtils.warningTimeOut) {
          val emailSubject = "埋点数据上报异常"
          val emailContent = "埋点数据上报异常：" + key
          val businessId = 10000
          val date = DateUtils.currentDate()
          BuryingPointForewarningDao.saveEmailWarningInfo(emailSubject, emailContent, businessId, date)
        }
      })
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

}
