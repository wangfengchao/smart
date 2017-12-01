package com.gis.launcher;

import com.alibaba.fastjson.JSONObject;
import com.gis.dao.BaiDuDistrictDB;
import com.gis.tools.BaiduMokatorToLngLat;
import com.gis.tools.PositionTransformTool;

import java.util.List;
import java.util.Map;

/**
 * 百度——板块边界
 * Created by fc.w on 2017/11/30.
 */
public class BaiDuDistrictPolyline {

    public static void main(String[] args) {
        BaiDuDistrictDB db = new BaiDuDistrictDB();
        BaiDuDistrictPolyline districtPolyline = new BaiDuDistrictPolyline();
        Map<Integer, String> uids = db.getUids();
        for (Integer id : uids.keySet()) {
            districtPolyline.getPolyline(id, uids.get(id));
        }
    }

    /**
     * 获取板块边界
     * @param id
     * @param uid
     */
    public void getPolyline(int id, String uid) {
        System.out.println("id: " + id + "  uid: " + uid);
        String url = "http://map.baidu.com/" +
                "?pcevaname=pc4.1" +
                "&qt=ext" +
                "&uid=" + uid +
                "&ext_ver=new" +
                "&l=12";
        BaiDuDistrictSearch ds = new BaiDuDistrictSearch();
        BaiDuDistrictDB db = new BaiDuDistrictDB();

        try {
            // 请求板块边界数据
            String result = ds.sendGet(url);
            JSONObject obj = JSONObject.parseObject(result);
            JSONObject content = obj.getJSONObject("content");
            String geo = content.getString("geo");
            /* 解析POI数据 */
            List<String> mocatorList = BaiduMokatorToLngLat.parseJeo(geo);
            if (null != mocatorList) {
                // 封装板块边界
                StringBuilder polyLine = new StringBuilder();
                for (int i = 0; i < mocatorList.size(); i++) {
                    String[] coordinate = mocatorList.get(i).split("\\#");
                    // 经纬度转换
                    Map<String, Double> location = BaiduMokatorToLngLat.convertMC2LL(Double.parseDouble(coordinate[0]), Double.parseDouble(coordinate[1]));
                    Double lng = location.get("lng");
                    Double lat = location.get("lat");
                    String coord = lng + "," + lat;
                    polyLine.append(coord);
                    if (i < mocatorList.size() - 1) {
                        polyLine.append(";");
                    }
                }
                // 百度转高德经纬度
                String gdPolyline = PositionTransformTool.bd2gcj_long(polyLine.toString());
                // 更新板块边界数据
                db.updatePolyline(id, geo, polyLine.toString(), gdPolyline);

            } else {
                System.out.println("没有边界");
                // 更新没有板块边界点状态
                db.updateNoPolylineSatatus(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
