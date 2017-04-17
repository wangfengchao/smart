package com.smart.processor;

import com.smart.pipline.QianlimaPipeline;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smart.site.QianlimaSite;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;


/**
 * Created by  fc.w on 2016/12/5.
 */
public class QianlimaProcessor implements PageProcessor {

    private static String URL = "http://center.qianlima.com/xm_searchdata.jsp?stype=1&uid=0&area=-1&xmlb=-1&jzjd=-1&xmlb=-1&zhuangxiu=-1&xmmc=&gxmc=&selectArea=-1&selectXmlb=-1&protype=2&findAgain=false";

    private static final String LIST_URL = "http://center.qianlima.com/xm_searchdata.jsp.*";
    private static final String DETAIL_URL = "http://www.qianlima.com/zb/detail/\\w+\\.html";
    private Site site = new QianlimaSite().getSite();

    /**
     * pg : 页数；
     * zijin = 商业类型
     * @param page
     */
    public void process(Page page) {
        if (page.getUrl().regex(LIST_URL).match()) {
            int totPage = Integer.parseInt(new JsonPathSelector("$.[*].totPage").select(page.getRawText()));
            for (int i = 1; i <= totPage; i++) {
                page.putField("resultPage"+i, page.getJson().toString());
                page.addTargetRequest(URL + "&pg=" + i + "&zijin=" + 1);
                page.addTargetRequest(URL + "&pg=" + i + "&zijin=" + 2);

                JSONArray results = JSON.parseArray(page.getJson().toString());
                for (int j = 0; j < results.size(); j++) {
                    JSONObject obj = results.getJSONObject(j);
                    page.addTargetRequest(obj.getString("url"));
                }
            }

        } else if (page.getUrl().regex(DETAIL_URL).match()) {
            page.putField("title", page.getHtml().xpath("//h2[@id='xiangmu2']"));
        }
    }

    public Site getSite() {
        return site;
    }

    //爬虫入口
    public static void spiderApp() {
        Spider.create(new QianlimaProcessor()).
                addUrl(URL + "1").
                addPipeline(new QianlimaPipeline()).
                thread(5).
                run();
    }

}
