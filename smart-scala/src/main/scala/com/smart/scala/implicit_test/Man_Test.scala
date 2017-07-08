package com.smart.scala.implicit_test

/**
  * Created by fc.w on 2017/6/26.
  */
object Man_Test {

  implicit def man2superman(man: Man): SuperMan = new SuperMan(man.name)

  def main(args: Array[String]): Unit = {
    val leo = new Man("tom")
    leo.emitLaster
  }

}
