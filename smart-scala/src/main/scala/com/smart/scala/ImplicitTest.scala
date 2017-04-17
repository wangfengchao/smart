package com.smart.scala

/**
  * 隐式函数
  * Created by fc.w on 2017/4/11.
  */
object ImplicitTest  extends App {

  implicit def a2Rich(a: A) = new RichA(a)

  val a = new A
  a.rich()

}
