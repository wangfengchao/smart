package com.smart.utils

import java.text.SimpleDateFormat
import java.util.Date

/**
  * Created by fc.w on 2017/4/28.
  */
object DateUtils {

  val TIME_FORMAT: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  def currentDate(): String = {
    val date = new Date()
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00")
    dateFormat.format(date)
  }

}
