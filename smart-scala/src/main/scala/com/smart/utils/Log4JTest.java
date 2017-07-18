package com.smart.utils;

import org.apache.log4j.Logger;

/**
 * 日志级别保存测试
 * Created by fc.w on 2017/7/12.
 */
public class Log4JTest {

    private static Logger pvLogger = Logger.getLogger("pvLog");
    private static Logger clickLogger = Logger.getLogger("clickLog");
    private static int i = 1;
    private static int j = 1;
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            clickTest();
            pvTest();
            Thread.sleep(10000L);
        }

    }

    private static void clickTest() {
        clickLogger.info("clickLogger test: " + i++);
    }

    private static void pvTest() {
        pvLogger.info("pvLogger test: " + j++);
    }

}
