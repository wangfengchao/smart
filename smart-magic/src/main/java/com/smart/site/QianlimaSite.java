package com.smart.site;

import us.codecraft.webmagic.Site;

/**
 * Created by  fc.w on 2016/12/5.
 */
public class QianlimaSite {

    private Site site = Site.me()
            .setSleepTime(3000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
}
