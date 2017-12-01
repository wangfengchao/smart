package com.smart.apps.utils

import org.apache.hadoop.hbase.HBaseConfiguration

/**
  * Created by fukuiwei on 2017/2/27.
  * Hbase 工具类
  */
object HbaseHandler {
  //从配置中心获取zk配置
  val quorum = "h5,h6,h7"
  val port = "2181"
  //创建hbase工具
  val myConf = HBaseConfiguration.create()
  myConf.set("hbase.zookeeper.quorum", quorum)
  myConf.set("hbase.zookeeper.property.clientPort", port)
  myConf.set("hbase.defaults.for.version.skip", "true")

//  def getConn(): Connection = {
//    val connn = ConnectionFactory.createConnection(myConf)
//    connn
//  }

}
