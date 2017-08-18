package com.smart.hbase

import org.apache.hadoop.hbase.client.{Put, Result}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HConstants, HBaseConfiguration}
import org.apache.hadoop.mapred.JobConf
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.{SparkContext, SparkConf}

/**
  * saveAsNewAPIHadoopDataset
  * Created by fc.w on 2017/8/18.
  */
object SparkToHbaseNew {

  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      System.err.println("Usage: SparkToHBaseNew <input file>")
      System.exit(1)
    }
    val conf = new SparkConf().setAppName("SparkToHBaseNew")
    val sc = new SparkContext(conf)

    val input = sc.textFile(args(0))
    val hConf = HBaseConfiguration.create()
    hConf.set(HConstants.ZOOKEEPER_QUORUM, "www.iteblog.com:2181")
    val jobConf = new JobConf(hConf, this.getClass)
    jobConf.set(TableOutputFormat.OUTPUT_TABLE, "iteblog")

    //设置job的输出格式
    val job = Job.getInstance(jobConf)
    job.setOutputKeyClass(classOf[ImmutableBytesWritable])
    job.setOutputValueClass(classOf[Result])
    job.setOutputFormatClass(classOf[TableOutputFormat[ImmutableBytesWritable]])

    val data = input.map { item =>
      val Array(key, value) = item.split("\t")
      val rowKey = key.reverse
      val put = new Put(Bytes.toBytes(rowKey))
      put.add(Bytes.toBytes("f1"), Bytes.toBytes("info"), Bytes.toBytes(value))
      (new ImmutableBytesWritable, put)
    }
    //保存到HBase表
    data.saveAsNewAPIHadoopDataset(job.getConfiguration)
    sc.stop()
  }

}
