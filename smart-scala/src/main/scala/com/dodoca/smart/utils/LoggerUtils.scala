package com.dodoca.smart.utils

import org.apache.log4j.Logger

/**
  * @author fengchao.wang 2016-11-01 18:02
  **/
trait LoggerUtils {

  lazy val logger = Logger.getLogger(this.getClass.getName)

  def info(info: => String): Unit = {
    if (logger.isInfoEnabled) {
      logger.info(info)
    }
  }

  def error(info: => String): Unit = {
    logger.error(info)
  }

  def error(info: => String, e: => Throwable): Unit = {
    logger.error(info, e)
  }

}
