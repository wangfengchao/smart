package com.smart.scala

import EnumRedType._

/**
  * Created by fc.w on 2017/4/11.
  */
object EnumTest extends App {


  def doWhat(color: EnumRedType) = {
    color match {
      case A => println("stop")
      case B => println("hurry up")
      case _ => println("go")
    }
  }

  println(doWhat(A))
  println(doWhat(B))
}
