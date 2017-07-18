package com.smart.utils;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;
/**
 *
 * Created by fc.w on 2017/7/12.
 */
public class Log4jUtil extends DailyRollingFileAppender {

    /**
     * 只判断是否相等，而不判断优先级
     * @param priority
     * @return
     */
    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        return this.getThreshold().equals(priority);
    }

}
