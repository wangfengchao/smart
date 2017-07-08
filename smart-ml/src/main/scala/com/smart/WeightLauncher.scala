package com.smart

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fc.w on 2017/7/3.
  */
object WeightLauncher {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("weight_launcher").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val sqlContext = new HiveContext(sc)

    /*浏览*/
    val browseSQL = s"select u.telephone, count(*) as count from lifang.mid_user_house_action u where u.dt between '20170601' and '20170703' and u.type = 'browse' group by u.telephone"
    sqlContext.sql(browseSQL).registerTempTable("browse_table")
    /*微聊*/
    val chatSQL = s"select u.telephone, count(*) as count from lifang.mid_user_house_action u where u.dt between '20170601' and '20170703' and u.type = 'chat' group by u.telephone"
    sqlContext.sql(chatSQL).registerTempTable("talk_table")
    /*收藏*/
    val collectSQL = s"select u.telephone, count(*) as count from lifang.mid_user_house_action u where u.dt between '20170601' and '20170703' and u.type = 'collect' group by u.telephone"
    sqlContext.sql(collectSQL).registerTempTable("collect_table")
    /*约看*/
    val userOrderSQL = s"select u.telephone, count(*) as count from lifang.mid_user_house_action u where u.dt between '20170601' and '20170703' and u.type = 'order' group by u.telephone"
    sqlContext.sql(userOrderSQL).registerTempTable("user_order_table")
    /*带看*/
    val takeSQL = s"select u.guest_phonenum, count(u.house_id) AS count from lifang.es_userorder u where status = 3 and u.create_time between '2017-06-01 00:00:00' and '2017-07-03 00:00:00' group by u.guest_phonenum"
    sqlContext.sql(takeSQL).registerTempTable("user_take_table")

    sqlContext.sql("select b.telephone, COALESCE(b.count, 0) AS browseCount , COALESCE(t.count, 0) AS chatCount, COALESCE(c.count, 0) AS collectCount, COALESCE(o.count, 0) AS orderCount, COALESCE(k.count, 0) AS takeCount  from browse_table b left join talk_table t ON  b.telephone = t.telephone " +
      " LEFT JOIN collect_table c ON  t.telephone = c.telephone  LEFT JOIN user_order_table o  ON c.telephone = o.telephone LEFT JOIN user_take_table k ON o.telephone = k.telephone")
      .registerTempTable("join_table")

    val browseInsertSQL = s"insert overwrite table lifang.weight_matrix select telephone, browseCount, chatCount, collectCount, orderCount, takeCount  from join_table"
    sqlContext.sql(browseInsertSQL)
  }

}
