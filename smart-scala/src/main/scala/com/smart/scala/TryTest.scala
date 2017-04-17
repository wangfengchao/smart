package com.smart.scala

import java.net.URL

import scala.concurrent.{Future, future}
import scala.io.Source
import scala.util.{Failure, Success}

/**
  * Created by  fc.w on 2017/2/16.
  */
object TryTest {

  def getContent(url: URL): Either[String, Source] =
    if (url.getHost.contains("google"))
      Left("Requested URL is blocked for the good of the people!")
    else
      Right(Source.fromURL(url))



}
