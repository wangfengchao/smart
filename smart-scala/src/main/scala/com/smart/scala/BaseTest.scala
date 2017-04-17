package com.smart.scala

/**
  * Created by  fc.w on 2017/2/4.
  */
object BaseTest {

  def main(args: Array[String]): Unit = {
    val person = new Person("aa")
    person.age = 20
    println(person.age)

    println(add(1, 2))
    println(sum1(2)(3))
    variableParam("scala", "aa")
  }

  /** 匿名函数 */
  def add = (x: Int, y: Int) => x + y

  /** 函数赋值给常量 */
  val sum = (x: Int, y: Int) => x + y

  /** 函数颗粒化 */
  def sum1(x: Int)(y: Int) = x + y

  /** 变长参数用法 */
  def variableParam(s: String*): Unit = {
    s.foreach(println)
  }

}
