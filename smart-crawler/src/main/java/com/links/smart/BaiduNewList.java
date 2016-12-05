package com.links.smart;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by  fc.w on 2016/12/2.
 */
public class BaiduNewList extends CrawlListPageBase {
    private static HashMap<String, String> params;

    /**
     * 添加相关头信息，对请求进行伪装
     */
    static {
        params = new HashMap<String, String>();
        params.put("Referer", "http://www.baidu.com");
        params.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
    }

    public BaiduNewList(String urlStr) throws IOException {
        super(urlStr, "utf-8", "get", params);
    }

    @Override
    public String getUrlRegexString() {
        return "• <a href=\"(.*?)\"";
    }

    @Override
    public int getUrlRegexStringNum() {
        return 1;
    }

    public static void main(String[] args) throws IOException {
        BaiduNewList baidu = new BaiduNewList("http://news.baidu.com/n?cmd=4&class=sportnews&pn=1&from=tab");
        for (String s : baidu.getPageUrls()) {
            System.out.println(s);
        }
    }
}
