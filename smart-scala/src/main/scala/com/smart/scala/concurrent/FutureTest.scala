package com.smart.scala.concurrent


import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.util.{Failure, Success}

/**
  * 并行 Future测试
  * Created by fc.w on 2017/5/2.
  */
object FutureTest extends App {

  val s = "hello"
  val f: Future[String] = future {
    s + " future!"
  }

  f onSuccess {
    case msg => println(msg)
  }

  f onComplete {
    case Success(t) => {
      println(t)
    }
    case Failure(e) => {
      println(s"An error has occured: $e.getMessage")
    }
  }

  println(s)

}
