package com.smart.algorithm.gaode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 获取省，市，区 边界点
 * Created by fc.w on 2017/11/29.
 */
public class MapPolyline {

    public static void main(String[] args) {
        MapSearch mapSearch = new MapSearch();
        MapDataDB db = new MapDataDB();
        List<String> names = db.getData();
        for (String name : names) {
            String result = mapSearch.sendGet(name, "all", 1);
            JSONObject resultObj = JSON.parseObject(result);
            JSONArray jsonArray = resultObj.getJSONArray("districts");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String polyline = obj.getString("polyline");
                db.updatePolyline(name, polyline);
            }

        }
    }



}
