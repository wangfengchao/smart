package com.dodoca.smart.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * PoolingHttpClientConnectionManager 为 Http链接管理类
 * Created by  fc.w on 2016/12/6.
 */
public class HttpClientUtil {

    private static PoolingHttpClientConnectionManager cm;
    private static int maxTotal           = 100;
    private static int defaultMaxPerRoute = 100;
    private static int readTimeout        = 1000 * 30;
    private static int connectTimeout     = 1000 * 30;

    static {
        ConnectionSocketFactory connFactory = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", connFactory)
                .register("https", sslsf)
                .build();

        cm = new PoolingHttpClientConnectionManager(registry);
        //最大连接数
        cm.setMaxTotal(maxTotal);
        //每个路由基础的连接
        cm.setDefaultMaxPerRoute(defaultMaxPerRoute);
    }

    /**
     * 获取HttpClient
     * @return
     */
    public HttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     * 设置HttpClient的链接超时时间以及数据的读取时间
     * @return
     */
    private RequestConfig getRequestConfig() {
        return RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(readTimeout).build();
    }

    private HttpGet createGetMethod(String url, Map<String, String> params, String charset, Map<String, String> headers) {
        HttpGet method = new HttpGet(url);
        method.setConfig(getRequestConfig());
        if (params != null) {
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                String key = entry.getKey();
                String value = entry.getValue();
                paramList.add(new BasicNameValuePair(key, value));
            }
            try {
                String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(paramList, charset));
                StringBuffer sb = new StringBuffer();
                sb.append(url);
                if (url.indexOf("?") > 0) {
                    sb.append("&");
                } else {
                    sb.append("?");
                }
                sb.append(paramStr);
                method.setURI(new URI(sb.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (headers != null) {
            Iterator<Map.Entry<String, String>> iter = headers.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                String key = entry.getKey();
                String value = entry.getValue();
                method.setHeader(key, value);
            }
        }
        return method;
    }

    private HttpPost createPostMethod(String url, Map<String, String> params, String charset, Map<String, String> headers) {
        HttpPost method = new HttpPost(url);
        method.setConfig(getRequestConfig());
        if (params != null) {
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                String key = entry.getKey();
                String value = entry.getValue();
                paramList.add(new BasicNameValuePair(key, value));
            }
            try {
                method.setEntity(new UrlEncodedFormEntity(paramList, charset));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (headers != null) {
            Iterator<Map.Entry<String, String>> iter = headers.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                String key = entry.getKey();
                String value = entry.getValue();
                method.setHeader(key, value);
            }
        }
        return method;
    }

//    private boolean execute(HttpUriRequest request) {
//        int n = maxConnectTimes;
//        while (n > 0) {
//            try {
//                HttpClient httpClient = getHttpClient();
//                HttpResponse response = httpClient.execute(request);
//                responseHeaders = response.getAllHeaders();
//                HttpEntity entity = response.getEntity();
//                InputStream inputStream = entity.getContent();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
//                StringBuffer stringBuffer = new StringBuffer();
//                String lineString = null;
//                while ((lineString = bufferedReader.readLine()) != null){
//                    stringBuffer.append(lineString);
//                    stringBuffer.append("\n");
//                }
//                pageSourceCode = stringBuffer.toString();
//                InputStream in =new ByteArrayInputStream(pageSourceCode.getBytes(charsetName));
//                String charset = CharsetUtil.getStreamCharset(in, charsetName);
//                if (!charsetName.toLowerCase().equals(charset.toLowerCase())) {
//                    pageSourceCode = new String(pageSourceCode.getBytes(charsetName), charset);
//                }
//                bufferedReader.close();
//                inputStream.close();
//                return true;
//            } catch (Exception e) {
//                e.printStackTrace();
//                n--;
//            }
//        }
//        return false;
//    }

    /**
     * @param url
     * @param params
     * @param getPost
     * @param charset 参数编码方式
     * @param headers
     * @return
     * @Description: 访问url
     */
//    public boolean execute(String url, Map<String, String> params, String getPost, String charset, Map<String, String> headers) {
//        if (getPost == null) {
//            return false;
//        }
//        getPost = getPost.toLowerCase();
//        if ("get".equals(getPost)) {
//            return executeByGet(url, params, charset, headers);
//        } else if ("post".equals(getPost)) {
//            return executeByPost(url, params, charset, headers);
//        }
//        return false;
//    }

    /**
     * @param url
     * @param params
     * @param charset 参数编码方式
     * @param headers
     * @return
     * @Description: post请求
     */
//    public boolean executeByPost(String url, Map<String, String> params, String charset, Map<String, String> headers) {
//        HttpPost method = createPostMethod(url, params, charset, headers);
//        return execute(method);
//    }

    /**
     * @param url
     * @param params
     * @param charset 参数编码方式
     * @param headers
     * @return
     * @Description: get请求
     */
//    public boolean executeByGet(String url, Map<String, String> params, String charset, Map<String, String> headers) {
//        HttpGet method = createGetMethod(url, params, charset, headers);
//        return execute(method);
//    }
}
