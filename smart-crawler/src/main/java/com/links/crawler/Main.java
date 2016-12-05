package com.links.crawler;

import java.util.ArrayList;

/**
 * Created by  fc.w on 2016/12/2.
 */
public class Main {
    public static void main(String[] args) {
        String url = "https://www.zhihu.com/explore/recommendations";
        String content = Spider.sendGet(url);
        ArrayList<ZhiHu> myZhihu = Spider.GetRecommendations(content);
        for (ZhiHu zhiHu : myZhihu) {
            System.out.println(zhiHu);
        }

    }
}
