package com.smart.scala.actor_test

/**
  * Created by fc.w on 2017/6/26.
  */
object UserMessage_Test {

  def main(args: Array[String]): Unit = {
    val user = new UserMessageActor
    user.start()
    user ! Register("reg", "123")
    user ! Login("reg1", "1234")
  }

}
