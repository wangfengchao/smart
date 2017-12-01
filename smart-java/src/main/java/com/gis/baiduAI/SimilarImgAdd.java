package com.gis.baiduAI;

import com.smart.algorithm.https.HttpClientUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 相似图检索—入库API
 * Created by fc.w on 2017/10/9.
 */
public class SimilarImgAdd {

    public static String imgAdd(String imgFile) {
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/realtime_search/similar/add";
        String accessToken = "24.f79e95cabe15b9c4589b0f98e7c65517.2592000.1510120255.282335-10101665";
        url += "?access_token=" + accessToken;
        String appid = "10101665";
        HttpClientUtil httpClientUtil =  new HttpClientUtil();
        String charset = "utf-8";

        try {

            File file = new File(imgFile);
            File [] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file1 = files[i];
                String imgUrl = imgFile + "\\" + file1.getName();
                String imgBase64 = Image2Base64.getImageStr(imgUrl);
                System.out.println(imgUrl);
                Map<String,String> createMap = new HashMap<String,String>();
                createMap.put("sub_lib", appid);
                createMap.put("brief", imgUrl);
                createMap.put("image", imgBase64);

                String httpOrgCreateTestRtn = httpClientUtil.sendPOST(url, createMap, charset);
                System.out.println("result:"+httpOrgCreateTestRtn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String[] args) {
        imgAdd("C:\\Users\\Administrator\\Desktop\\fangduo");
    }


}
