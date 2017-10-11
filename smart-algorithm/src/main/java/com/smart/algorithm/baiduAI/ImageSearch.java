package com.smart.algorithm.baiduAI;

import com.smart.algorithm.https.HttpClientUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 相同图检索——检索
 * Created by fc.w on 2017/9/7.
 */
public class ImageSearch {

    private String charset = "utf-8";
    private HttpClientUtil httpClientUtil = null;

    public ImageSearch(){
        httpClientUtil = new HttpClientUtil();
    }

    /**
     * 图片检索接口
     * @param imgUrl
     * @throws IOException
     */
    public void imgSearch(String imgUrl) throws Exception {
        String url = "https://aip.baidubce.com/rest/2.0/realtime_search/same_hq/search";
        String accessToken = "24.f79e95cabe15b9c4589b0f98e7c65517.2592000.1510120255.282335-10101665";
        url += "?access_token=" + accessToken;
        String appid = "10101665";
        String imgBase64 = Image2Base64.getImageStr(imgUrl);

        Map<String,String> createMap = new HashMap<String,String>();
        createMap.put("sub_lib", appid);
        createMap.put("image", imgBase64);

        String httpOrgCreateTestRtn = httpClientUtil.sendPOST(url, createMap, charset);
        System.out.println("result:"+httpOrgCreateTestRtn);
    }

    public static void main(String[] args) throws Exception {
        ImageSearch main = new ImageSearch();
        String imgUrl1 = "C:\\Users\\Administrator\\Desktop\\诸葛找房\\6_3.jpg";
//        String imgUrl2 = "C:\\Users\\Administrator\\Desktop\\诸葛找房\\2_2.jpg";
//        String imgUrl3 = "C:\\Users\\Administrator\\Desktop\\诸葛找房\\2_3.jpg";
//        String imgUrl4 = "C:\\Users\\Administrator\\Desktop\\诸葛找房\\2_4.jpg";
//        String imgUrl5 = "C:\\Users\\Administrator\\Desktop\\诸葛找房\\2_5.jpg";
//        String imgUrl6 = "C:\\Users\\Administrator\\Desktop\\诸葛找房\\2_6.jpg";
//        String imgUrl7 = "C:\\Users\\Administrator\\Desktop\\诸葛找房\\2_7.jpg";
//        String imgUrl8 = "C:\\Users\\Administrator\\Desktop\\诸葛找房\\2_8.jpg";
//        String imgUrl9 = "C:\\Users\\Administrator\\Desktop\\诸葛找房\\2_9.jpg";

        main.imgSearch(imgUrl1);
//        main.imgSearch(imgUrl2);
//        main.imgSearch(imgUrl3);
//        main.imgSearch(imgUrl4);
//        main.imgSearch(imgUrl5);
//        main.imgSearch(imgUrl6);
//        main.imgSearch(imgUrl7);
//        main.imgSearch(imgUrl8);
//        main.imgSearch(imgUrl9);

    }

}
