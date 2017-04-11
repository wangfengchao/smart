package com.smart.sparkSQL.cache_way

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * hive 表缓存方式
  *
  * Created by fc.w on 2017/4/11.
  */
object CacheTableLauncher {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("cache_hive_table_launcher")
    val sc = new SparkContext(conf)
    val hiveContext = new HiveContext(sc)

    // 1. 缓存全表
    val peopleDF = hiveContext.sql("CACHE TABLE mid.people")
    peopleDF.show()

    // 2. 缓存过滤结果
    val filterResultDF = hiveContext.sql("CACHE TABLE people_cached as select * from mid.people where age > 20")
    filterResultDF.show()

    // 3. CACHE TABLE是即时生效(eager)的，如果你想等到一个action操作再缓存数据可以使用CACHE LAZY TABLE,这样操作会直到一个action操作才被触发，
    hiveContext.sql("CACHE LAZY TABLE people_cached as select * from mid.people where age > 20")

    // 4. 取消hive表缓存数据
    hiveContext.sql("UNCACHE TABLE people_cached as select * from mid.people where age > 20")



    val df = hiveContext.sql("select * from mid.people")
    df.registerTempTable("cache_people")
    // cacheTable操作是lazy的，需要一个action操作来触发缓存操作。
    hiveContext.cacheTable("cache_people")
    // 取消缓存
    hiveContext.uncacheTable("cache_people")
  }

}
