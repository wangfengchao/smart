package com.smart.processor;

import com.smart.site.ImgSite;
import com.smart.pipline.ImgPipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by  fc.w on 2016/12/6.
 */
public class ImgProcessor implements PageProcessor {

    private static String URL = "http://md.itlun.cn/index.html";

    private Site site = new ImgSite().getSite();

    public void process(Page page) {
        if (page.getUrl().get().equals(URL)) {
            List<String> urls = page.getHtml().xpath("//div[@id=\"s_tab\"]/a/@href").regex("http://md.itlun.cn/a/.*/").all();
            page.addTargetRequests(urls);
        } else if (page.getUrl().regex("http://md.itlun.cn/a/.*/").match()) {
            if (page.getUrl().regex("http://md.itlun.cn/a/.*/list_\\d+\\_\\d+\\.html").match()) {
                List<String> urls = page.getHtml().xpath("//ul[@class='pic2']/li/a/@href").all();
                page.addTargetRequests(urls);
            } else {
                List<String> urls = page.getHtml().xpath("//div[@class='cShowPage']/li/a/@href").all();
                if (!urls.isEmpty()) {
                    String url0 = urls.get(0);
                    String[] suburls = url0.split(".html");
                    String suburl = suburls[0];
                    suburl = suburl.substring(0, suburl.length() - 1) + "1.html";
                    urls.add(suburl);
                    page.addTargetRequests(urls);
                }
            }

//            else if (page.getUrl().regex("http://md.itlun.cn/a/.*/\\d+\\.html").match()) {
//                List<String> urls = page.getHtml().xpath("//img/@src").regex("http://.*").all();
//                for(String url : urls) {
//                    System.out.println(url);
//                }
//                page.putField("imgUrls", urls);
//            }
        }

        //最终图片
        List<String> urls = page.getHtml().xpath("//img/@src").regex("http://.*").all();
            page.putField("imgUrls", urls);

        for(String url : urls) {
            System.out.println(url);
        }
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ImgProcessor())
                .addUrl(URL)
                .thread(5)
                .addPipeline(new ImgPipeline())
                .run();
    }
}
