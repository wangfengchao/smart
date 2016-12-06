package com.dodoca.smart.processor;

import com.dodoca.smart.site.CookieSite;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by  fc.w on 2016/12/6.
 */
public class CookieProcessor implements PageProcessor {

    private Site site = new CookieSite().getSite();

    public void process(Page page) {
        page.putField("aboutme", page.getHtml().xpath("//textarea[@id='aboutme']/text()").toString());
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new CookieProcessor())
                // 从"http://www.imooc.com/user/setprofile"开始抓
                .addUrl("http://www.imooc.com/user/setprofile").addPipeline(new ConsolePipeline())
                // 开启5个线程抓取
                .thread(1)
                // 启动爬虫
                .run();
    }
}
