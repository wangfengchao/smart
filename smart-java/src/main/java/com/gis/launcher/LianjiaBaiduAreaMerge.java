package com.gis.launcher;

import com.gis.dao.BaiDuDistrictDB;
import com.gis.dao.LianjiaBaiduAreaMergeDB;
import com.gis.tools.PositionTransformTool;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 链家和百度板块合并
 * Created by fc.w on 2017/12/4.
 */
public class LianjiaBaiduAreaMerge {

    public static void main(String[] args) {
        LianjiaBaiduAreaMergeDB ljdb = new LianjiaBaiduAreaMergeDB();  // 获取链家板块名称
        BaiDuDistrictDB bdAreaDB = new BaiDuDistrictDB();  // 获取百度板块名称
        Map<String, List<String>> ljResult = ljdb.getCityList();

        for (String cityName : ljResult.keySet()) {
            List<String> ljAreaNames = ljResult.get(cityName);
            // 匹配板块
            for (String ljAreaName : ljAreaNames) {
                int id = bdAreaDB.isDistrictInfo(cityName, ljAreaName);
                if (id > 0) {
                    // 获取链接板块ID和边界
                    Map<Integer, String> idPolyline = ljdb.getPolyline(cityName, ljAreaName);
                    for (int areaId : idPolyline.keySet()) {
                        try {
                            String polyline = idPolyline.get(areaId);
                            // 百度转高德
                            String gdPolyline = PositionTransformTool.bd2gcj_long(polyline);
                            int result = bdAreaDB.updatePolylineByLianJia(id, polyline, gdPolyline, 2);
                            if (result > 0) {
                                // 更新是否已同步状态
                                ljdb.updateStatus(areaId);
                                System.out.println( "lj areaId: " + areaId + "   ljAreaName: " + ljAreaName +  "  Success !!!");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }
            }

        }


    }





}
