package com.smart.cf

import scopt.OptionParser

/**
  * 协同过滤 ALS
  * Created by fc.w on 2017/5/25.
  */
object CollaborativeFilteringALS {

  /**
    * @param input
    * @param kryo
    * @param numIterations 是迭代的次数。
    * @param numBlocks 是用于并行化计算的分块个数 (设置为-1，为自动配置)。
    * @param lambda 是ALS的正则化参数。
    * @param rank 是模型中隐语义因子的个数。
    * @param implicitPrefs 决定了是用显性反馈ALS的版本还是用适用隐性反馈数据集的版本。
    * @param delimiter
    */
  case class Params(
    input: String = null,
    kryo: Boolean = false,
    numIterations: Int = 20,
    numBlocks: Int = 2,
    lambda: Double = 1.0,
    rank: Int = 10,
    implicitPrefs: Boolean = false,
    delimiter: String= "\\s++"
   )

  def main(args: Array[String]): Unit = {
    val defaultParams = Params()

    val parse = new OptionParser[Params]("CollaborativeFilteringALS") {
      head("CollaborativeFiltering ALS: an example app for ALS on Amazon data.")

    }

  }

}
