package com.dodoca.smart.site;

import us.codecraft.webmagic.Site;

/**
 * Created by  fc.w on 2016/12/6.
 */
public class CookieSite {

    private Site site = Site.me()
            //添加cookie之前一定要先设置主机地址，否则cookie信息不生效
            .setDomain("www.imooc.com")
            //添加抓包获取的cookie信息
            .addCookie("apsid","NhMjQ5MzAwN2JiZmY0ZDg3MGFhNjJmNDU1ZjkzN2YAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANDU0OTYxMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAyODIxODkwODBAcXEuY29tAAAAAAAAAAAAAAAAAAAAADBkMjgwMzJjN2ZkYTE3M2QxMTA5ZjY5NGYzNjE3YWJifz5GWH8%2BRlg%3DMW")
            .addCookie("cvde", "58463d9b94c56-14")
            .addCookie("IMCDNS", "0")
            .addCookie("imooc_isnew", "1")
            .addCookie("imooc_isnew_ct", "1480998299")
            .addCookie("imooc_uuid", "eafd94d4-437b-444d-8798-63a3a5aba948")
            .addCookie("last_login_username", "282189080%40qq.com")
            .addCookie("loginstate", "1")
            .addCookie("PHPSESSID", "sqsv2onhni2tf9htjvuc9oli64")
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
            .setCharset("UTF-8");

    public Site getSite() {
        return site;
    }
}
