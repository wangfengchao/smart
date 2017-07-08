package com.smart.scala.actor_test

import scala.actors.Actor

/**
  * Created by fc.w on 2017/6/26.
  */
class UserMessageActor extends Actor {

  override def act(): Unit = {
    while (true) {
      receive {
        case Login(name, pass) => println(s"login, $name, $pass")
        case Register(name, pass) => println(s"register, $name, $pass")
      }
    }
  }


}

case class Login(name: String, pass: String)
case class Register(name: String, pass: String)
