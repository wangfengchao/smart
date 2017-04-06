package com.smart.algorithm.processor;

import com.smart.algorithm.pipline.MofcomPipeline;
import com.smart.algorithm.site.MofcomSite;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by  fc.w on 2016/12/6.
 */
public class MofcomProcessor implements PageProcessor {

    private static String URL = "http://www.mofcom.gov.cn/mofcom/guobiebaogao.shtml";

    private Site site = new MofcomSite().getSite();

    public void process(Page page) {
        if (page.getUrl().regex(URL).match()) {
            List<String> lists = page.getHtml().xpath("//tr/td/*/tidyText()").all();
            for (String str : lists) {
                if (str.contains("<")) {
                    String[] splitStr = str.split(" ");
                    String url = splitStr[1].substring(1, splitStr[1].length()-1);
                    page.putField(splitStr[0], url);
                    page.addTargetRequest(url);
                } else {
                    String[] strs = str.split("\\(");
                    int num = Integer.parseInt(strs[1].substring(0, strs[1].length() - 2));
                    page.putField(strs[0],num);
                }

            }
        } else if (!page.getUrl().regex("www.*").match()) {
//            System.out.println(page.getUrl().get());

        }

    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new MofcomProcessor())
                .addUrl(URL)
                .addRequest()
                .addPipeline(new MofcomPipeline())
                .thread(5)
                .run();
    }
}
