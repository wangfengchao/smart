package com.smart.house_portrayal

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 房源画像之热搜
  * Created by fc.w on 2017/9/13.
  */
object HousePortrayalHotSearch {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("HousePortrayaHotSearch")
    val sc = new SparkContext(conf)
    val sqlContext = new HiveContext(sc)

    val house_pv_dwell_sql = "select city_id, house_id, count(*) as pv, sum(residence_time) / 1000 as dwell_time  from  wkdm.dm_wkzf_mid_bp_house  where type='pv' and pid = 1067 and house_category = 2 group by city_id, house_id"
    val house_collect_sql = "select city_id, house_id, count(*) as coll_count from wkdm.dm_wkzf_mid_bp_house where type='click' and pid in ('1067026', '1067041')  and house_category = 2 group by city_id, house_id"
    val merge_data_sql = "select p.city_id, p.house_id, p.pv, if(p.dwell_time is NULL, 0, p.dwell_time) as dwell_time, if(c.coll_count is NULL, 0, c.coll_count) as coll_count from tmp_house_pv p left join  tmp_house_collect c on p.city_id = c.city_id and p.house_id = c.house_id"
    sqlContext.sql(house_pv_dwell_sql).registerTempTable("tmp_house_pv")
    sqlContext.sql(house_collect_sql).registerTempTable("tmp_house_collect")
    sqlContext.sql(merge_data_sql).registerTempTable("merge_house_data")

    sqlContext.sql(s"insert overwrite table wkdw.dw_m_bp_merge_house select city_id, house_id, pv, dwell_time, coll_count from merge_house_data")

  }

}
