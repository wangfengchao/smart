package com.smart.model

/**
  * Created by josie on 2016/11/21.
  */
object DataModel {

  /**
    *
    * @param productType  产品类型
    * @param osPlant 设备
    * @param paramNames 参数名称
    * @param nameId 页面名称ID 或 事件名称ID
    * @param nameType 类型：事件、页面
    */
  case class BuryingPointModel(productType:String, osPlant: String, paramNames: String, nameId: String, nameType: String)

}
