package com.smart.spark.utils

import java.sql.Connection
import org.apache.commons.dbcp.BasicDataSource

/**
  * Created by fc.w on 2017/3/30.
  */
object DBUtils {

  private val dbcp = new BasicDataSource()
  private val thread =  new ThreadLocal[Connection]()

  dbcp.setDriverClassName(CommonUtils.driver)
  dbcp.setUrl(CommonUtils.mysqlUrl)
  dbcp.setUsername(CommonUtils.userName)
  dbcp.setPassword(CommonUtils.password)
  dbcp.setInitialSize(CommonUtils.initSize.toInt)
  dbcp.setMaxActive(CommonUtils.maxActive.toInt)
  dbcp.setMaxWait(CommonUtils.maxWait.toLong)
  dbcp.setMinIdle(CommonUtils.maxIdle.toInt)
  dbcp.setMaxIdle(CommonUtils.maxIdle.toInt)

  /**
    * 打开链接
    * @return
    */
  def getConnection(): Connection = {
    val conn = dbcp.getConnection()
    thread.set(conn)
    conn
  }

  /**
    * 关闭链接
    */
  def closeConnection(): Unit = {
    val conn = thread.get()
    try {
      if (conn != null) {
        conn.close()
        thread.remove()
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

}
