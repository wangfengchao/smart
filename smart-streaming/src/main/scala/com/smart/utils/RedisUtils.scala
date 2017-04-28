package com.smart.utils

import org.apache.commons.lang.StringUtils
import redis.clients.jedis.Jedis

object RedisUtils {

  def getRedis: Jedis = {
    var jedis: Jedis = null
    try {
      jedis = new Jedis(SparkUtils.r_ip, SparkUtils.r_port)
      if (StringUtils.isNotBlank(SparkUtils.r_password)) jedis.auth(SparkUtils.r_password)
    } catch {
      case e: Exception => println("获取链接错误!", e.printStackTrace())
        None
    }
    jedis
  }

}
