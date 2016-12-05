package com.links.httpClient;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by  fc.w on 2016/12/2.
 */
public class Spider1 {
    private static HttpClient httpClient = new HttpClient();
    public static boolean downloadPage(String path) throws IOException {
        InputStream input = null;
        OutputStream output = null;
        GetMethod getMethod = new GetMethod(path);
        int statusCode = httpClient.executeMethod(getMethod);
        if (statusCode == HttpStatus.SC_OK) {
            input = getMethod.getResponseBodyAsStream();
            String fileName = path.substring(path.lastIndexOf("/") + 1) + ".html";
            output = new FileOutputStream(fileName);
            int tempByte = -1;
            while ((tempByte = input.read()) > 0) {
                output.write(tempByte);
            }
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            return true;
        }
        return  false;
    }

    public static void main(String[] args) {
        try {
            Spider1.downloadPage("http://www.baidu.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
