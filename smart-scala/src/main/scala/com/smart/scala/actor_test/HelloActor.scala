package com.smart.scala.actor_test

import scala.actors.Actor

/**
  * Created by fc.w on 2017/6/26.
  */
class HelloActor extends Actor {

  override def act(): Unit = {
    while (true) {
      receive {
        case name: String => println(s"Hello, ${name}")
      }
    }
  }

}
