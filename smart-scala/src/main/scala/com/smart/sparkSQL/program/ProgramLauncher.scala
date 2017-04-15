package com.smart.sparkSQL.program

import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fc.w on 2017/4/11.
  */
object ProgramLauncher {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("program_launcher")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    //Row
    val people = sc.textFile(args(0)).map(_.split(",")).map(p => Row(p(0), p(1).trim))
    //定义schema列名, Generate the schema
    val schemaString = "name age"
    val structFields = schemaString.split(" ").map(fieldName => StructField(fieldName, StringType, true))
    val schema = StructType(structFields)

    val peopleDataFrame = sqlContext.createDataFrame(people, schema)
    peopleDataFrame.registerTempTable("people")
    val results = sqlContext.sql("SELECT name FROM people")
    results.map(t => "Name: " + t(0)).collect().foreach(println)
  }

}
