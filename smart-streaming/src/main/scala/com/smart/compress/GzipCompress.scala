package com.smart.compress

import java.text.SimpleDateFormat
import java.util.Calendar

import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.compress.BZip2Codec
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.{Logger, LoggerFactory}

/**
  * 1. HDFS文件压缩,
  * 2. 压缩后删除源HDFS数据
  * 3. 压缩数据按月归档
  * Created by fc.w on 2017/7/17.
  */
object GzipCompress {

  @transient private val log_ : Logger = LoggerFactory.getLogger(GzipCompress.getClass.getName.stripSuffix("$"))

  def main(args: Array[String]): Unit = {
    if (args.size != 2) {
      System.err.println("Error!\n usage: .jar [srcPath] [descPath]")
      return ;
    }

    val conf = new SparkConf().setAppName("rdd_launcher")
    val sc = new SparkContext(conf)

    try {
      for (i <- (198 to 297).reverse) {
        // 0. 获取HDFS文件路径
        val date = getNAgoday(i, "yyyy-MM-dd")
        val dataPath = s"${args(0)}/${date}/"
        log_.info(s"源数据文件路径: ${dataPath}")

        // 1. 文件压缩BZip2格式
        val data = sc.textFile(dataPath)
        data.saveAsTextFile(s"${args(1)}/${date}", classOf[BZip2Codec])

        // 2. 删除原HDFS数据
        log_.info("start delete data ...")
        val path = new Path(dataPath)
        val hadoopConf = sc.hadoopConfiguration
        val hdfs = org.apache.hadoop.fs.FileSystem.get(hadoopConf)
        if (hdfs.exists(path)) {
          hdfs.delete(path, true)
        }
        log_.info("success....")
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }

    sc.stop()
  }

  /**
    * 获取前N天时间
    * @param n
    * @return
    */
  def getNAgoday(n: Int, format: String): String = {
    val n2 = if (n < 0) 0 else n
    val cal: Calendar = Calendar.getInstance()
    cal.add(Calendar.DATE, -n2)
    val dateFormat: SimpleDateFormat = new SimpleDateFormat(format)
    val dayStr = dateFormat.format(cal.getTime)
    dayStr
  }


}
