package com.smart.apps.utils

import org.apache.commons.lang.StringUtils
import redis.clients.jedis.Jedis


/**
  * Created by fukuiwei on 2017/2/6.
  * redis 工具类
  */
object RedisTools {

  val redis_ip = "h5"
  val redis_port = "7000".toInt
  val redis_password = "FxjfIcbm92XtQt59YGaX1AVST7_Yrc"

  def getRedis: Jedis = {
    var jedis: Jedis = null
    try {
      jedis = new Jedis(redis_ip, redis_port)
      if (StringUtils.isNotBlank(redis_password)) jedis.auth(redis_password)
    } catch {
      case e: Exception => println("获取链接错误!", e.printStackTrace())
    }
    jedis
  }


}
