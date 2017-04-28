package com.smart.dao

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

import com.lifang.common.util.DateUtils
import com.lifang.point.spark.DataModel.BuryingPointModel
import com.lifang.point.utils.DBUtils
import org.apache.commons.lang.StringUtils
import org.apache.spark.streaming.kafka.OffsetRange

/**
  * 埋点预警数据处理类
  * Created by fc.w on 2017/4/27.
  */
object BuryingPointForewarningDao {

  /**
    * Save KafkaOffset into Mysql
    * @param offsetRanges
    */
  def updateKafkaOffset(offsetRanges: Array[OffsetRange]): Unit = {
    try {
      println("----------kafkaOffset2Mysql--------------")
      val conn = DBUtils.getConnection()
      val stmt = conn.createStatement
      val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      val date = new Date()
      for (o <- offsetRanges) {
        println(s"1.-------topic: ${o.topic}  partition: ${o.partition}   fromOffset: ${o.fromOffset}    untilOffset: ${o.untilOffset}")
        stmt.executeUpdate("update kafka_offsets set kafka_offset = " + o.untilOffset + ", dt = \""+ sdf.format(date) +"\" where kafka_topic = \"" + o.topic + "\" and kafka_partition = " + o.partition)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      DBUtils.closeConnection()
    }
  }

  /**
    * 保存埋点参数名称
    * @param model
    */
  def saveParamName(model: BuryingPointModel): Unit = {
    println(s"saveParamName------  productType：${model.productType}   osPlant：${model.osPlant}   nameType: ${model.nameType}   nameId：${model.nameId}     paramNames：${model.paramNames}")
    try {
      val date = DateUtils.currentDate()
      val conn = DBUtils.getConnection()
      val stmt = conn.prepareStatement("insert into p_param_warning(product_type, os_plant, name_type, name_id, param_name, dt) values (?, ?, ?, ?, ?, ?)")
      stmt.setString(1, model.productType)
      stmt.setString(2, model.osPlant)
      stmt.setString(3, model.nameType)
      stmt.setInt(4, if (!StringUtils.isEmpty(model.nameId)) model.nameId.toInt else 0)
      stmt.setString(5, if (!StringUtils.isEmpty(model.paramNames)) model.paramNames else "")
      stmt.setTimestamp(6, new Timestamp(new Date(DateUtils.dateTimeToLong(date)).getTime))

      stmt.executeUpdate()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      DBUtils.closeConnection()
    }
  }

  /**
    * 保存邮件告警信息
    * @param subject 邮件主题
    * @param content 邮件内容
    * @param busId 业务ID
    * @param date 日期
    */
  def saveEmailWarningInfo(subject: String, content: String, busId: Int, date: String): Unit = {
    println(s"saveEmailWarningInfo------  ${subject}   ${content}   ${busId}     ${date}")
    try {
      val conn = DBUtils.getConnection()
      val stmt = conn.prepareStatement("insert into email_warning(email_subject, email_content, bus_id, dt) values (?, ?, ?, ?)")
      stmt.setString(1, subject)
      stmt.setString(2, content)
      stmt.setInt(3, busId)
      stmt.setTimestamp(4, new Timestamp(new Date(DateUtils.dateTimeToLong(date)).getTime))

      stmt.executeUpdate()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      DBUtils.closeConnection()
    }
  }

}
