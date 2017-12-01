package com.smart.algorithm.gaode.baidu;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 百度——板块边界
 * Created by fc.w on 2017/11/30.
 */
public class DistrictPolyline {

    public static void main(String[] args) {
        DistrictDB db = new DistrictDB();
        DistrictPolyline districtPolyline = new DistrictPolyline();
        Map<Integer, String> uids = db.getUids();
        for (Integer id : uids.keySet()) {
            districtPolyline.getPolyline(id, uids.get(id));
        }
//        districtPolyline.getPolyline(75035, "80638ace788fde2479dbd201");

    }

    /**
     *
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
        DistrictSearch ds = new DistrictSearch();
        DistrictDB db = new DistrictDB();

        try {
            // 请求板块边界数据
            String result = ds.sendGet(url);
            JSONObject obj = JSONObject.parseObject(result);
            JSONObject content = obj.getJSONObject("content");
            String geo = content.getString("geo");
            /* 解析POI数据 */
            List<String> mocatorList = Baidu.parseJeo(geo);
            if (null != mocatorList) {
                // 封装板块边界
                StringBuilder polyLine = new StringBuilder();
                for (int i = 0; i < mocatorList.size(); i++) {
                    String[] coordinate = mocatorList.get(i).split("\\#");
                    // 经纬度转换
                    Map<String, Double> location = Baidu.convertMC2LL(Double.parseDouble(coordinate[0]), Double.parseDouble(coordinate[1]));
                    Double lng = location.get("lng");
                    Double lat = location.get("lat");
                    String coord = lng + "," + lat;
                    polyLine.append(coord);
                    if (i < mocatorList.size() - 1) {
                        polyLine.append(";");
                    }
                }
                // 百度转高德经纬度
                String gdPolyline = PositionTransformUtils.bd2gcj_long(polyLine.toString());
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
