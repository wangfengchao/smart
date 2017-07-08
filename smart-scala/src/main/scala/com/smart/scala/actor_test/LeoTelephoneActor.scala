package com.smart.scala.actor_test

import scala.actors.Actor

/**
  * Created by fc.w on 2017/6/26.
  */
case class Message(content: String, sender: Actor)

class LeoTelephoneActor extends Actor {

  override def act(): Unit = {
    while (true) {
    }
  }

}
