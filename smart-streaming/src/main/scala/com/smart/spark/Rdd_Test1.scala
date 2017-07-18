package com.smart.spark

import org.apache.hadoop.io.compress.BZip2Codec
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fc.w on 2017/6/16.
  */
object Rdd_Test1 {
  Logger.getRootLogger.setLevel(Level.WARN)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("rdd_launcher").setMaster("local[1]")
    val sc = new SparkContext(conf)

    // 1.rdd 创建操作
    val data = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val distData = sc.parallelize(data, 2)
    println(distData)

    /* 读取hdfs数据 */
    val distFile1 = sc.textFile("hdfs://h1:8020/user/root/input01/")
    val distFile2 = sc.textFile("hdfs://h1:8020/user/root/input01/*.txt")
    val distFile3 = sc.textFile("hdfs://h1:8020/user/root/input01/01,txt")
    /* 读取本地数据 */
    val distFile4 = sc.textFile("/home/data/")
    val distFile5 = sc.textFile("/home/data/01.txt")
    val distFile6 = sc.textFile("/home/data/*.txt")


    // 2. RDD转换操作
    val rdd1 = sc.parallelize(1 to 9, 3)
    val rdd2 = rdd1.map(_ * 2)
    rdd2.collect()

    val rdd3 = rdd2.filter(_ > 10)
    rdd3.collect()

    val rdd4 = rdd3.flatMap(_ to 20)
    rdd4.collect()

    val rdd5 = rdd1.mapPartitions(myfunc)
    rdd5.collect()
    val rdd6 = rdd1.mapPartitionsWithIndex(myfunc1)
    rdd6.collect()

    val rdd7 = sc.parallelize(1 to 10000, 3)
    rdd7.sample(false, 0.1, 0).count()

    //就是将两个RDD进行合并，不去重
    val rdd8 = rdd1.union(rdd3)
    rdd8.collect()

    //返回两个RDD的交集，并且去重
    val rdd9 = rdd8.intersection(rdd1)
    rdd9.collect()

    // 返回在RDD中出现，并且不在otherRDD中出现的元素，不去重
    val rdd20_1 = sc.parallelize(1 to 9, 3)
    val rdd20_2 = sc.parallelize(1 to 3, 3)
    rdd20_1.subtract(rdd20_2).collect()


    // K V
    val rdd0 = sc.parallelize(Array((1, 1), (1, 2), (1, 3), (2, 1), (2, 2), (2, 3)))
    val rdd11 = rdd0.groupByKey()
    rdd11.collect()

    //reduce是一个action，和reduceByKey完全不同，reduceByKey是transformation操作。
    val rdd12 = rdd0.reduceByKey(_ + _)
    rdd12.collect()

    //两个分区：分区1：(1, 3), (1, 2)  分区2： (1, 4), (2, 3) ，每个分区相同key的value与初始值比较获取最大值，分区1（1,3），分区2：（1,4）， （2,3），然后相同key相加：（1,7） （2,3）
    val z = sc.parallelize(List((1, 3), (1, 2), (1, 4), (2, 3)))
    z.aggregateByKey(0)(math.max(_, _), _ + _).collect

    //两个分区,分区1：1,2,3  分区2:4,5,6  每个分区每个元素与初始值比较获取最大的赋给初始值。 然后两个分区的最大值相加 ：3 + 6 = 9
    val a = sc.parallelize(List(1 ,2 ,3 ,4 ,5 ,6),2)
    a.aggregate(0)(math.max(_, _), _ + _)

    val data2 = sc.parallelize(Array((1, 1.0), (1, 2.0), (1, 3.0), (2, 1.0), (2, 2.0), (3, 3.0)), 2)
    val combine1 = data2.combineByKey(createCombiner = (v: Double) => (v, 1),
      mergeValue = (c:(Double, Int), v: Double) => (c._1 + v, c._2 + 1),
      mergeCombiners = (c1: (Double, Int), c2: (Double, Int)) => (c1._1 + c2._1, c1._2 + c2._2),
      numPartitions = 2
    )

    val rdd14 = rdd0.sortByKey()
    rdd14.collect()

    val idName = sc.parallelize(Array((1, "zhangsan"),(1, "2ngsan"), (2, "lisi"), (3, "wangwu")))
    val idAge = sc.parallelize(Array((1, 30), (2, 29), (4, 21)))

    println("\n内关联（inner join）有相同KEY时会出现笛卡尔集\n")
    val rdd15 = idName.join(idAge)
    rdd15.collect()

    println("\n左外关联（left out join）\n")
    val rdd16 = idName.leftOuterJoin(idAge)
    rdd16.collect()

    println("\n右外关联（right outer join）\n")
    idName.rightOuterJoin(idAge).collect()

    println("\n全外关联（full outer join）\n")
    idName.fullOuterJoin(idAge).collect

    val rdd17 = idName.cogroup(idAge).collect()

    // shell 命令
    val rdd18 = sc.parallelize(1 to 9, 3)
    rdd18.pipe("head -n 1").collect()

    val rdd19 = rdd1.randomSplit(Array(0.3, 0.7), 1)
    rdd19(0).collect()
    rdd19(1).collect()


      //用于将两个RDD组合成Key/Value形式的RDD,这里默认两个RDD的partition数量以及元素数量都相同
    val rdd21_1 = sc.parallelize(Array(1, 2, 3, 4), 2)
    val rdd21_2 = sc.parallelize(Array('a', 'b', 'c', 'd'), 2)
    val rdd21_3 = sc.parallelize(Array('A', 'B', 'C', 'D'), 2)
    rdd21_1.zip(rdd21_2).collect()

    rdd21_1.zipPartitions(rdd21_2, rdd21_3) {
      (rdd1Iter, rdd2Iter, rdd3Iter) => {
        var result = List[String]()
        while(rdd1Iter.hasNext && rdd2Iter.hasNext && rdd3Iter.hasNext) {
          result::=(rdd1Iter.next() + "_" + rdd2Iter.next() + "_" + rdd3Iter.next())
        }
        result.iterator
      }
    }.collect()

  }

  def myfunc1(index: Int, iter: Iterator[Int]): Iterator[String] = {
    iter.toList.map(x => index+"-"+x).iterator
  }

  def myfunc[T](iter: Iterator[T]): Iterator[(T, T)] = {
    var res = List[(T, T)]()
    var pre = iter.next()
    while (iter.hasNext) {
      val cur = iter.next()
      res ::=  (pre, cur)
      pre = cur
    }

    res.iterator
  }

}
