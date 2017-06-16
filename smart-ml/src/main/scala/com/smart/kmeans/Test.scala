package com.smart.kmeans

import org.apache.spark.mllib.feature.Normalizer
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fc.w on 2017/5/24.
  */
object Test {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("UserActiveLauncher")
    val sc = new SparkContext(conf)
    val sqlContext = new HiveContext(sc)

    val hi = s"select distinct(cityid) from lifang.es_es_houses  where houseState = 2 and touchTime is not null and checkstate = 1 and cityid IS NOT NULL and datediff(from_unixtime(unix_timestamp(),'yyyy-MM-dd'),to_date(touchTime))<40 and verifiedStatus =4"
    val row = sqlContext.sql(hi).collect()
    row.foreach(r => {
      val cityID = r.getLong(0)
      val sql = s"insert overwrite table lifang.house_matrix partition(cityid = ${cityID}) select houseid, townid, sellprice, spacearea, livingroomsum, bedroomsum , wcsum from lifang.es_es_houses  where houseState = 2 and touchTime is not null and datediff(from_unixtime(unix_timestamp(),'yyyy-MM-dd'),to_date(touchTime))<40 and verifiedStatus =4 and checkstate = 1 and cityid=${cityID}"
      sqlContext.sql(sql)
    })

    val shanghaiSQL = s"select townid, sellprice, spacearea, livingroomsum, bedroomsum, wcsum from lifang.house_matrix where cityid = 43"
    sqlContext.sql(shanghaiSQL).write.mode(SaveMode.Overwrite).format("text").save("/user/root/mlib/43.txt")








//    val data = sqlContext.sql("").collect()
    val data = MLUtils.loadLibSVMFile(sc, "/user/root/mlib/sample_libsvm_data.txt")
    val normalizer1 = new Normalizer()
    val normalizer2 = new Normalizer(p = Double.PositiveInfinity)
    // Each sample in data1 will be normalized using $L^2$ norm.
    val data1 = data.map(x => (x.label, normalizer1.transform(x.features)))
    data1.map(f => f._1).collect()
    // Each sample in data2 will be normalized using $L^\infty$ norm.
    val data2 = data.map(x => (x.label, normalizer2.transform(x.features)))
    data2.map(f => f._1).collect()
  }

}
