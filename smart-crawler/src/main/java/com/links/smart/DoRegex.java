package com.links.smart;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则处理工具
 * Created by  fc.w on 2016/12/2.
 */
public class DoRegex {

    private static String rootUrlRegex = "(http://.*?/)";
    private static String currentUrlRegex = "(http://.*/)";
    private static String ChRegex = "([\u4e00-\u9fa5]+)";

    /**
     * @param dealStr
     * @param regexStr
     * @param splitStr
     * @param n
     * @return String
     * @Description: 正则匹配结果，每条记录用splitStr分割
     */
    public static String getString(String dealStr, String regexStr, String splitStr, int n){
        String reStr = "";
        if (dealStr == null || regexStr == null || n < 1 || dealStr.isEmpty()){
            return reStr;
        }
        splitStr = (splitStr == null) ? "" : splitStr;
        Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(dealStr);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            stringBuffer.append(matcher.group(n).trim());
            stringBuffer.append(splitStr);
        }
        reStr = stringBuffer.toString();
        if (splitStr != "" && reStr.endsWith(splitStr)){
            reStr = reStr.substring(0, reStr.length() - splitStr.length());
        }
        return reStr;
    }

    /**
     * @param dealStr
     * @param regexStr
     * @param n
     * @return String
     * @Description: 正则匹配结果，将所有匹配记录组装成字符串
     */
    public static String getString(String dealStr, String regexStr, int n){
        return getString(dealStr, regexStr, null, n);
    }

    /**
     * @param dealStr
     * @param regexStr
     * @param n
     * @return String
     * @Description: 正则匹配第一条结果
     */
    public static String getFirstString(String dealStr, String regexStr, int n){
        if (dealStr == null || regexStr == null || n < 1 || dealStr.isEmpty()){
            return "";
        }
        Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(dealStr);
        while (matcher.find()) {
            return matcher.group(n).trim();
        }
        return "";
    }

    /**
     * @param dealStr
     * @param regexStr
     * @param n
     * @return ArrayList<String>
     * @Description: 正则匹配结果，将匹配结果组装成数组
     */
    public static List<String> getList(String dealStr, String regexStr, int n){
        List<String> reArrayList = new ArrayList<String>();
        if (dealStr == null || regexStr == null || n < 1 || dealStr.isEmpty()){
            return reArrayList;
        }
        Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(dealStr);
        while (matcher.find()) {
            reArrayList.add(matcher.group(n).trim());
        }
        return reArrayList;
    }

    /**
     * @param url
     * @param currentUrl
     * @return String
     * @Description: 组装网址，网页的url
     */
    private static String getHttpUrl(String url, String currentUrl){
        try {
            url = encodeUrlCh(url);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (url.indexOf("http") == 0){
            return url;
        }
        if  (url.indexOf("/") == 0){
            return getFirstString(currentUrl, rootUrlRegex, 1) + url.substring(1);
        }
        return getFirstString(currentUrl, currentUrlRegex, 1) + url;
    }

    /**
     * @param dealStr
     * @param regexStr
     * @param currentUrl
     * @param n
     * @return ArrayList<String>
     * @Description: 获取和正则匹配的绝对链接地址
     */
    public static List<String> getArrayList(String dealStr, String regexStr, String currentUrl, int n){
        List<String> reArrayList = new ArrayList<String>();
        if (dealStr == null || regexStr == null || n < 1 || dealStr.isEmpty()){
            return reArrayList;
        }
        Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(dealStr);
        while (matcher.find()) {
            reArrayList.add(getHttpUrl(matcher.group(n).trim(), currentUrl));
        }
        return reArrayList;
    }

    /**
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     * @Description: 将连接地址中的中文进行编码处理
     */
    public static String encodeUrlCh (String url) throws UnsupportedEncodingException {
        while (true) {
            String s = getFirstString(url, ChRegex, 1);
            if ("".equals(s)){
                return url;
            }
            url = url.replaceAll(s, URLEncoder.encode(s, "utf-8"));
        }
    }

    /**
     * @param dealStr
     * @param regexStr
     * @param array 正则位置数组
     * @return
     * @Description: 获取全部
     */
    public static List<String[]> getListArray(String dealStr, String regexStr, int[] array) {
        List<String[]> reArrayList = new ArrayList<String[]>();
        if (dealStr == null || regexStr == null || array == null) {
            return reArrayList;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 1) {
                return reArrayList;
            }
        }
        Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(dealStr);
        while (matcher.find()) {
            String[] ss = new String[array.length];
            for (int i = 0; i < array.length; i++) {
                ss[i] = matcher.group(array[i]).trim();
            }
            reArrayList.add(ss);
        }
        return reArrayList;
    }

    /**
     * @param dealStr
     * @param regexStr
     * @param array
     * @return
     * @Description: 获取全部
     */
    public static List<String> getStringArray(String dealStr, String regexStr, int[] array) {
        List<String> reStringList = new ArrayList<String>();
        if (dealStr == null || regexStr == null || array == null) {
            return reStringList;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 1) {
                return reStringList;
            }
        }
        Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(dealStr);
        while (matcher.find()) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; i++) {
                sb.append(matcher.group(array[i]).trim());
            }
            reStringList.add(sb.toString());
        }
        return reStringList;
    }

    /**
     * @param dealStr
     * @param regexStr
     * @param array 正则位置数组
     * @return
     * @Description: 获取第一个
     */
    public static String[] getFirstArray(String dealStr, String regexStr, int[] array) {
        if (dealStr == null || regexStr == null || array == null) {
            return null;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 1) {
                return null;
            }
        }
        Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(dealStr);
        while (matcher.find()) {
            String[] ss = new String[array.length];
            for (int i = 0; i < array.length; i++) {
                ss[i] = matcher.group(array[i]).trim();
            }
            return ss;
        }
        return null;
    }
}
