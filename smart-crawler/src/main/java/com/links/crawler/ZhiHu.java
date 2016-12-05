package com.links.crawler;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by  fc.w on 2016/12/2.
 */
public class ZhiHu {
    public String question;
    public String questionDescription;
    public String zhihuUrl;
    public ArrayList<String> answers;

    public ZhiHu(String url) {
        question = "";
        questionDescription = "";
        zhihuUrl = "";
        answers = new ArrayList<String>();

        if (getRealUrl(url)) {
            System.out.println("正在抓取" + zhihuUrl);
            // 根据url获取该问答的细节
            String content = Spider.sendGet(zhihuUrl);
            Pattern pattern;
            Matcher matcher;
            // 匹配标题
            pattern = Pattern.compile("zh-question-title.+?<h2.+?>(.+?)</h2>");
            matcher = pattern.matcher(content);
            if (matcher.find()) {
                question = matcher.group(1);
            }

            //匹配描述
            pattern = Pattern.compile("zh-question-detail.+?<div.+?>(.*?)</div>");
            matcher = pattern.matcher(content);
            if (matcher.find()) {
                question = matcher.group(1);
            }

            // 匹配答案
            pattern = Pattern.compile("/answer/content.+?<div.+?>(.*?)</div>");
            matcher = pattern.matcher(content);
            boolean isFind = matcher.find();
            while (isFind) {
                answers.add(matcher.group(1));
                isFind = matcher.find();
            }


        }

    }

    boolean getRealUrl(String url) {
        Pattern pattern = Pattern.compile("question/(.*?)/");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            zhihuUrl = "http://www.zhihu.com/question/" + matcher.group(1);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "问题：" + question + "\n描述：" + questionDescription + "\n链接：" + zhihuUrl + "\n回答：" + answers.size() + "\n";
    }
}
