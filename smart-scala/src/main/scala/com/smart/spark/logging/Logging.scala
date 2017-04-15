package com.smart.spark.logging

import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by fc.w on 2017/3/9.
  */
private[com] trait Logging {

  @transient private var log_ : Logger = null

  /** 获取对象名，忽略$后缀的对象名 */
  protected def logName = {
    this.getClass.getName.stripSuffix("$")
  }

  /**  创建或获取logger */
  protected def log: Logger = {
    if (log_ == null) {
      log_ = LoggerFactory.getLogger(logName)
    }
    log_
  }

  protected def logInfo(msg: String): Unit = {
    if (log.isInfoEnabled) log.info(msg)
  }

  protected def logDebug(msg: String): Unit = {
    if (log.isDebugEnabled) log.debug(msg)
  }

  protected def logError(msg: String): Unit = {
    if (log.isErrorEnabled) log.debug(msg)
  }

  protected def logWarning(msg: String): Unit = {
    if (log.isWarnEnabled) log.warn(msg)
  }

  protected def logTrace(msg: String): Unit = {
    if (log.isTraceEnabled) log.trace(msg)
  }

}
