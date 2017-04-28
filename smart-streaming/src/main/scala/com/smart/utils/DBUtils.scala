package com.smart.utils

import java.sql.Connection

/**
  * Created by fc.w on 2017/3/30.
  */
object DBUtils {

  private val dbcp = new BasicDataSource()
  private val thread =  new ThreadLocal[Connection]()

  dbcp.setDriverClassName(SparkUtils.driver)
  dbcp.setUrl(SparkUtils.url)
  dbcp.setUsername(SparkUtils.user)
  dbcp.setPassword(SparkUtils.password)
  dbcp.setInitialSize(SparkUtils.initSize)
  dbcp.setMaxActive(SparkUtils.maxActive)
  dbcp.setMaxWait(SparkUtils.maxWait.toLong)
  dbcp.setMinIdle(SparkUtils.maxIdle)
  dbcp.setMaxIdle(SparkUtils.maxIdle)

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
