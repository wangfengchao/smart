package com.dodoca.smart.utils

import joptsimple.OptionParser

/**
  * Created by fengchao.wang on 2016/11/02 
  **/
object CommandUtils extends LoggerUtils {

  def printUsageAndDie(parser: OptionParser, message: String): Nothing = {
    System.err.println(message)
    parser.printHelpOn(System.err)
    sys.exit()
  }


}
