package com.smart.algorithm.https;

import com.smart.algorithm.baiduAI.Image2Base64;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fc.w on 2017/9/6.
 */
public class TestMain {

    private String charset = "utf-8";
    private HttpClientUtil httpClientUtil = null;

    public TestMain(){
        httpClientUtil = new HttpClientUtil();
    }

    public void test() throws Exception {
        String url = "https://aip.baidubce.com/rest/2.0/realtime_search/same/add";
        String accessToken = "24.732bef029fee60461d5786751ea487ad.2592000.1507342455.282335-10101665";
        String appid = "10101665";
        String imgBase64 = Image2Base64.getImageStr("D:\\10_6.jpg");
        url += "?access_token=" + accessToken;
        Map<String,String> createMap = new HashMap<String,String>();
        createMap.put("sub_lib", appid);
        createMap.put("image", imgBase64);

        String httpOrgCreateTestRtn = httpClientUtil.sendPOST(url, createMap, charset);
        System.out.println("result:"+httpOrgCreateTestRtn);
    }

    public static void main(String[] args) throws Exception {
        TestMain main = new TestMain();
        main.test();
    }

}
