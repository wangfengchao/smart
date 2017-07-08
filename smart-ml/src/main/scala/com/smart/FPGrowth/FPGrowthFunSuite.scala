package com.smart.FPGrowth

import com.smart.utils.SparkLearningFunSuite
import org.apache.spark.mllib.fpm.FPGrowth

/**
  * 商品关联规则挖掘。
  * 版本二
  * Created by fc.w on 2017/6/21.
  */
class FPGrowthFunSuite  extends SparkLearningFunSuite {

  test("FPGrowthFunSuite  Test") {
//    val path = System.getProperty("user.dir")
//    val data = sc.textFile(s"${path}/src/main/resources/data/fpgrowth.txt")
//    val transactions = data.map(_.trim.split(" ")).cache()

    val data = sc.textFile("hdfs://h1:8020/user/root/fpgData")
    val transactions = data.map(_.trim.split(",")).cache()
    val minSupport = 0.05
    val minConfidence = 0.05
    val numPartition = 1

    val fpg = new FPGrowth()
      .setMinSupport(minSupport)
      .setNumPartitions(numPartition)
    val model = fpg.run(transactions)

    // 频繁项集
    model.freqItemsets.collect().filter(_.items.size >= 1).foreach(itemset =>
      println(itemset.items.mkString("[", ",", "]") + ", " + itemset.freq)
    )

    // 得到符合置信度的项集，并打印应该推荐的商品:  [用户浏览的商品] -> [推荐商品]， [置信度]
    model.generateAssociationRules(minConfidence).collect().foreach(rule =>
      println(rule.antecedent.mkString("[", ",", "]") + " => " + rule.consequent.mkString("[", ",", "]") + ", " + rule.confidence)
    )

  }

}
