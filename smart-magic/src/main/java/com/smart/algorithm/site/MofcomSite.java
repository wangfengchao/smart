package com.smart.algorithm.site;

import us.codecraft.webmagic.Site;

/**
 * Created by  fc.w on 2016/12/6.
 */
public class MofcomSite {

    private  Site site = Site.me()
            .setDomain("www.mofcom.gov.cn")
            .setCycleRetryTimes(3)
            .setRetryTimes(3)
            .setSleepTime(500)
            .setTimeOut(3 * 60 * 1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
            .setCharset("UTF-8");

    public Site getSite() {
        return site;
    }

}
