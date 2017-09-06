package com.smart.img

import java.sql.{Connection, DriverManager}

/**
  * jdbc 链接工具
  */
object JDBCHelper {
  //mysql 配置初始化
  val url = "jdbc:mysql://121.196.221.169:3306/report?useUnicode=true&characterEncoding=UTF-8"
  val user = "hadoop"
  val password = "qazhZCF4WVs6iLTvy3Be"

  println("--jdbc--",url,user,password)

  Class.forName("com.mysql.jdbc.Driver")
  /**
    * 获取数据库连接
    *
    * @return
    */
  def getConn(): Connection = {
    val conn = DriverManager.getConnection(url, user, password)
    conn
  }

  /**
    * 获取自定义数据库链接
    *
    * @param url
    * @param username
    * @param password
    * @return
    */
  def getDefConn(url: String, username: String, password: String): Connection = {

    DriverManager.getConnection(url, username, password)
  }


}
