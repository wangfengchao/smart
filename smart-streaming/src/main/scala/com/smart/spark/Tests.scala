package com.smart.spark

/**
  * Created by fc.w on 2017/6/23.
  */
object Tests extends App {

  def main(args: Array[String]): Unit = {
    val test = new Test2("tom")
    test.sayHello


    val test1 = Test2("jerry")
    test1.sayHello
  }

}
