package com.smart.spark

/**
  * Created by fc.w on 2017/6/23.
  */
class Test2(name: String) {

  def sayHello = println(s"Hi, ${name} == ${Test2.num}")

}


object  Test2 {
  private val num = 2

  def apply(name: String) = new Test2(name)
}

