package com.links.httpClient;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by  fc.w on 2016/12/2.
 */
public class DownTool {

    /**
     * 根据 URL 和网页类型生成需要保存的网页的文件名，去除 URL 中的非文件名字符
     * @param url
     * @param contentType
     * @return
     */
    private String getFileNameByUrl(String url, String contentType) {
        // 移除 "http://" 这七个字符
        url = url.substring(7);
        //确认抓取到的页面为text/html类型
        if (contentType.indexOf("html") != -1) {
            url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
        } else {
            url = url.replaceAll("[\\?/:*|<>\"]", "_") + "." + contentType.substring(contentType.lastIndexOf("/") + 1);
        }
        return url;
    }

    /**
     * 保存网页字节数组到本地文件，filePath 为要保存的文件的相对地址
     * @param data
     * @param filePath
     */
    private void saveToLocal(byte[] data, String filePath) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(filePath)));
            for (int i = 0; i < data.length; i++)
                out.write(data[i]);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 下载 URL 指向的网页
    public String downloadFile(String url) {
        String filePath = null;
        // 1.生成 HttpClinet对象并设置参数
        HttpClient httpClient = new HttpClient();
        // 设置 HTTP连接超时 5s
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        // 2.生成 GetMethod对象并设置参数
        GetMethod getMethod = new GetMethod(url);
        // 设置 get请求超时 5s
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        // 设置请求重试处理
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());
        // 3.执行GET请求
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            // 判断访问的状态码
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + getMethod.getStatusLine());
                filePath = null;
            }
            // 4.处理 HTTP 响应内容
            byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
            // 根据网页 url 生成保存时的文件名
            filePath = "temp\\"
                    + getFileNameByUrl(url,
                    getMethod.getResponseHeader("Content-Type").getValue());
            saveToLocal(responseBody, filePath);
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("请检查你的http地址是否正确");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            // 释放连接
            getMethod.releaseConnection();
        }
        return filePath;
    }

}
