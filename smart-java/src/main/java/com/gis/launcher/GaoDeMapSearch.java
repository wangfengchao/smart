package com.gis.launcher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gis.dao.GaoDeMapDataDB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 高德
 * 爬取 国家、省/直辖市 、市、区/县 街道、镇 信息
 * Created by fc.w on 2017/11/29.
 */
public class GaoDeMapSearch {

    private static final String KEY = "3be52ae13a839b18ffa33552afcf49ae";

    public static void main(String[] args) {
        getDistrictData("中国", 1);
    }

    /**
     * 获取 国家、省/直辖市2个级别数据
     * @param keyword
     * @param subdistrict
     */
    private static void getDistrictData (String keyword, int subdistrict) {
        GaoDeMapSearch mapSearch = new GaoDeMapSearch();
        String result = mapSearch.sendGet(keyword, "base", subdistrict);
        JSONObject resultObj = JSON.parseObject(result);
        JSONArray jsonArray = resultObj.getJSONArray("districts");
        jsonParse(jsonArray);
    }

    /**
     * 获取 市、区/县 街道、镇3个级别数据
     * @param keyword
     * @param subdistrict
     */
    private static void getStreetData(String keyword, int subdistrict) {
        GaoDeMapSearch mapSearch = new GaoDeMapSearch();
        String result = mapSearch.sendGet(keyword, "base", subdistrict);
        JSONObject resultObj = JSON.parseObject(result);
        JSONArray jsonArray = resultObj.getJSONArray("districts");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            JSONArray  districts = obj.getJSONArray("districts");
            jsonParse(districts);
        }
    }

    /**
     * 解析JSON，并存储
     * @param jsonArray
     */
    private static void jsonParse(JSONArray jsonArray) {
        GaoDeMapDataDB db = new GaoDeMapDataDB();
        try {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String citycode = obj.getString("citycode");
                // 城市编码
                if (citycode.equals("[]")) {
                    citycode = "0";
                }
                String adCode = obj.getString("adcode");  // 行政区编码
                String name = obj.getString("name");     // 行政区名称
                String center = obj.getString("center"); //行政区中心点
                String[] logLat = center.split(",");
                float lon = Float.parseFloat(logLat[0]); // 经度
                float lat = Float.parseFloat(logLat[1]); // 纬度
                String levelStr = obj.getString("level");
                int level = 0; //行政区级别
                if (levelStr.equals("country")) {
                    level = 1;
                } else if (levelStr.equals("province")) {
                    level = 2;
                } else if (levelStr.equals("city")) {
                    level = 3;
                } else if (levelStr.equals("district")) {
                    level = 4;
                } else if (levelStr.equals("street")) {
                    level = 5;
                }
                // 添加数据
                db.addData(citycode, adCode, name, level, lon, lat);
                // 下级行政区列表
                JSONArray districtsJSON = obj.getJSONArray("districts");
                // 递归解析数据
                if (districtsJSON.size() > 0) {
                    jsonParse(districtsJSON);
                }

                if (level == 2) {
                    getStreetData(name, 3);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * GET请求
     * @param keyword
     * @param subdistrict
     * @return
     */
    String sendGet(String keyword, String extensions, int subdistrict) {
        String url = "http://restapi.amap.com/v3/config/district?";
        url = url + "key=" + KEY
                + "&keywords=" + keyword
                + "&subdistrict=" + subdistrict
                + "&extensions=" + extensions;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return result;
    }

}
