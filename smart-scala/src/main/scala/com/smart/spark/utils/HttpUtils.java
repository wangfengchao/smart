package com.smart.spark.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HTTP
 * Created by fc.w on 2017/4/1.
 */
public class HttpUtils {

    private final String USER_AGENT = "Mozilla/5.0";

    /**
     * 获取 HTTP 链接
     * @param url
     * @return
     */
    private HttpURLConnection getHttpConnFunc(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestProperty("User-Agent", USER_AGENT);

        return con;
    }

    /**
     * 发送GET方式请求
     * @param url
     * @return
     * @throws Exception
     */
    public String sendGet(String url) throws Exception {
        HttpURLConnection con = getHttpConnFunc(url);
        con.setRequestMethod("GET");

        return readResponseBody(con.getInputStream());
    }

    /**
     * 读取响应数据流
     * @param inputStream
     * @return
     */
    private String readResponseBody(InputStream inputStream) throws IOException {
        StringBuffer response = new StringBuffer();
        String inputLine;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

}
