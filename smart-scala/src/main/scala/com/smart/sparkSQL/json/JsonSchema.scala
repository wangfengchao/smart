package com.smart.sparkSQL.json

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fc.w on 2017/4/11.
  */
object JsonSchema {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("json_launcher")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val struct = StructType(
      StructField("partner_code", StringType, true) ::
      StructField("app_name", StringType, true) ::
      StructField("person_info", MapType(StringType, StringType, true), true) ::
      StructField("items", ArrayType(MapType(StringType, StringType, true)), true) :: Nil
    )

    val df = sqlContext.read.schema(struct).json(args(0))
    df.printSchema
    df.show()
  }

}
