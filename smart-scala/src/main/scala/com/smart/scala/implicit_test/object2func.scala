package com.smart.scala.implicit_test

/**
  * Created by fc.w on 2017/6/26.
  */
class object2func {

  implicit def object2SpecialPersion(obj: Object): SpecialPersion = {
    var spe: SpecialPersion = null
    if (obj.getClass == classOf[Student]) {
      val stu = obj.asInstanceOf[Student]
      spe = new SpecialPersion(stu.name)
    } else if (obj.getClass == classOf[Older]) {
      val old = obj.asInstanceOf[Older]
      spe = new SpecialPersion(old.name)
    }
    spe
  }

}
