package com.smart.scala

/**
  * 隐式参数
  * Created by fc.w on 2017/4/11.
  */
object ImplicitParam extends App {

  def testPara(implicit name: String): Unit = {
    println(name)
  }

  implicit val name = "aaaaaa"

  testPara
  testPara("bbbb")

}
