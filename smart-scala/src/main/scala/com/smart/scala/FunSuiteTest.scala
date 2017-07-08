package com.smart.scala

import org.scalatest.{BeforeAndAfterAll, FunSuite, Suite}

/**
  * Created by fc.w on 2017/6/21.
  */
class FunSuiteTest extends FunSuite {

  test("elem result should have passed width") {
    val ele = List(1, 2, 3)
    assert(ele.length == 2)
  }

}
