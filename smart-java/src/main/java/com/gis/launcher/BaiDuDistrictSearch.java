package com.gis.launcher;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gis.dao.BaiDuDistrictDB;
import com.gis.dao.GaoDeMapDataDB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 百度
 * 获取区域信息
 * Created by fc.w on 2017/11/30.
 */
public class BaiDuDistrictSearch {


    public static void main(String[] args) {
        BaiDuDistrictSearch districtSearch = new BaiDuDistrictSearch();
        GaoDeMapDataDB mapDataDB = new GaoDeMapDataDB();
        Map<Integer, String> cityNameList =  mapDataDB.getCityData();
        for (Integer cityId : cityNameList.keySet()) {
            districtSearch.getDistrictInfo(cityId, cityNameList.get(cityId));
        }

    }

    /**
     * 根据城市获取板块数据
     * @param cityName
     */
    public void getDistrictInfo(int cityId, String cityName) {
        System.out.println("cityId :  "+ cityId +"   cityName: "+ cityName +" ing...");
        int pageSize = 20; // 每页显示数据大小
        int pageNum = 0; // 当前页数
        BaiDuDistrictSearch districtSearch = new BaiDuDistrictSearch();
        // 获取第一页板块数据
        String initResult = districtSearch.pageDistrictSearch(cityName, pageSize, pageNum);
        JSONObject initObj = JSONObject.parseObject(initResult);
        JSONArray initResults = initObj.getJSONArray("results");
        if (null != initResults || initResults.size() > 0) {
            districtSearch.parseJSON(cityId, cityName, initResults);
        } else {
            System.out.println("Results NULL   cityId :  "+ cityId +"   cityName: "+ cityName );
        }


        int totalNum = initObj.getIntValue("total");
        int sumPage = (totalNum / pageSize) - 1; // 总页数
        // 从第2页开始获取板块数据
        for (int i = 1; i <= sumPage; i++) {
            String pageResult = districtSearch.pageDistrictSearch(cityName, pageSize, i);
            JSONObject totalObj = JSONObject.parseObject(pageResult);
            JSONArray results = totalObj.getJSONArray("results");
            if (null != results || results.size() > 0) {
                districtSearch.parseJSON(cityId, cityName, results);
            } else {
                System.out.println("Page  Results NULL   cityId :  "+ cityId +"   cityName: "+ cityName );
            }

        }

    }

    /**
     * 解析并保存板块数据
     * @param districts
     */
    public void parseJSON( int cityId, String cityName, JSONArray districts) {
        BaiDuDistrictDB db = new BaiDuDistrictDB();
        for (int i = 0; i < districts.size(); i++) {
            try {
                JSONObject districtObj = districts.getJSONObject(i);
                String uid = districtObj.getString("uid");
                if (uid == null) continue;
                String districtName = districtObj.getString("name");
                JSONObject locationObj = districtObj.getJSONObject("location");
                float lat = locationObj.getFloatValue("lat");
                float lng = locationObj.getFloatValue("lng");
                String address = districtObj.getString("address");

                // 保存板块数据
                db.addDistrictInfo(cityId, cityName, districtName, lat, lng, address, uid);
            } catch (Exception e) {
                System.out.println("ERROR:  cityId: " + cityId + "   cityName:" + cityName + "   districts: "+ districts);
                e.printStackTrace();
            }
        }
    }


    /**
     * 根据城市获取板块信息
     * @param city
     */
    public String pageDistrictSearch(String city, int pageSize, int pageNum) {
        String url = "http://api.map.baidu.com/place/v2/search" +
                "?query=乡镇" +
                "&tag=乡镇" +
                "&page_size=" + pageSize +
                "&page_num=" + pageNum +
                "&region=" + city +
                "&city_limit=true" +
                "&output=json" +
                "&ak=BIB0GGwvOINZOlBs1GpsG9o6yYpGhO9c";
        BaiDuDistrictSearch districtSearch = new BaiDuDistrictSearch();
        String result = districtSearch.sendGet(url);
        return result;
    }


    /**
     * GET请求
     * @param url
     * @return
     */
    public String sendGet(String url) {
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
