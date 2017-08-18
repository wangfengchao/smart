package com.smart.hbase

import org.apache.hadoop.hbase.{HConstants, HBaseConfiguration}
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapred.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapred.JobConf
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Spark中内置提供了两个方法可以将数据写入到Hbase：（1）、saveAsHadoopDataset；（2）、saveAsNewAPIHadoopDataset
  * saveAsHadoopDataset： Output the RDD to any Hadoop-supported storage system, using a Hadoop JobConf object for that storage system.
  *     The JobConf should set an OutputFormat and any output paths required (e.g. a table name to write to) in the same way as it would be configured for a
  *     Hadoop MapReduce job.
　* saveAsNewAPIHadoopDataset： Output the RDD to any Hadoop-supported storage system with new Hadoop API,
  *     using a Hadoop Configuration object for that storage system. The Conf should set an OutputFormat and any output paths required
  *     (e.g. a table name to write to) in the same way as it would be configured for a Hadoop MapReduce job.
  *
  * 可以看出这两个API分别是针对mapred和mapreduce实现的
  *
  * 通过Spark将计算好的数据存储到Hbase中
  * Created by fc.w on 2017/8/18.
  */
object SparkToHBase {

  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      System.err.println("Usage: SparkToHBase <input file>")
      System.exit(1)
    }

    val conf = new SparkConf().setAppName("SparkToHBase")
    val sc = new SparkContext(conf)

    val input = sc.textFile(args(0))

    //创建HBase配置
    val hConf = HBaseConfiguration.create()
    hConf.set(HConstants.ZOOKEEPER_QUORUM, "www.iteblog.com:2181")

    //创建JobConf，设置输出格式和表名
    val jobConf = new JobConf(hConf, this.getClass)
    jobConf.setOutputFormat(classOf[TableOutputFormat])
    jobConf.set(TableOutputFormat.OUTPUT_TABLE, "iteblog")

    val data = input.map { item =>
      val Array(key, value) = item.split("\t")
      val rowKey = key.reverse
      val put = new Put(Bytes.toBytes(rowKey))
      put.add(Bytes.toBytes("f1"), Bytes.toBytes("info"), Bytes.toBytes(value))
      (new ImmutableBytesWritable, put)
    }
    //保存到HBase表
    data.saveAsHadoopDataset(jobConf)
    sc.stop()
  }

}
