package com.smart.apps

/**
  * Created by fc.w on 2017/8/10.
  */
object RiskUtils {

  // checkpoint
  val ristCheckpointDir = "/user/checkpoint/risk"
  val riskAppName = "Risk_Controller_Launcher"
  // 时间
  val batchDuration = 60
  // group
  val consumer_group = "risk_group"

  val userTopics = Set("test_pv")

  // 分钟
  val paramBatchTime = 60

  //
  val userAgentMap = Set("", "")

}
