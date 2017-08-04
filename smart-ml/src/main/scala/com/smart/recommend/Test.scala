package com.smart.recommend

/**
  * Created by fc.w on 2017/8/3.
  */
object Test {

  def main(args: Array[String]): Unit = {
    for (i <- 0 to 30) {
      val n = (new util.Random).nextInt(10)
      print(n)
    }

  }

}
