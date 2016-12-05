package com.dodoca.smart

import com.dodoca.smart.utils.CommandUtils
import joptsimple.OptionParser

/**
  * @author fengchao.wang
  * @create 2016-11-01 13:56
  **/
object LoadData {

  def main(args: Array[String]): Unit = {
    val optionParser = new OptionParser
    val overrideOpt = optionParser.accepts("aaa", "aaa")
      .withRequiredArg()
      .ofType(classOf[String])
    val overrideOpt1 = optionParser.accepts("bbb", "bbb")
      .withRequiredArg()
      .ofType(classOf[String])
    if (args.length == 0) {
      CommandUtils.printUsageAndDie(optionParser, "USAGE: java -jar smart-scala-1.0-SNAPSHOT-shaded.jar  args[0]")
    }

//     LoadDataSource.parseData(args(0))
  }

}
