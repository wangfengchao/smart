package com.gis.launcher;

import com.gis.dao.LianJiaLngLatConvertDao;
import com.gis.tools.PositionTransformTool;
import com.gis.vo.LianJiaVO;

import java.util.List;

/**
 * 经纬度转换
 * Created by fc.w on 2017/12/5.
 */
public class LianJiaLngLatConvert {

    public static void main(String[] args) {

        LianJiaLngLatConvertDao dao = new LianJiaLngLatConvertDao();
        List<LianJiaVO> result = dao.getPolyline();
        for (LianJiaVO vo : result) {
            // 百度转高德经纬度
            String gdPolyline = PositionTransformTool.bd2gcj_long(vo.getPolyline());
            String gdLng = PositionTransformTool.bd2gcj(vo.getLng().toString(), vo.getLat().toString());
            String[] lngLat = gdLng.split(",");
            float lng = Float.parseFloat(lngLat[0]);
            float lat = Float.parseFloat(lngLat[1]);
            dao.updateLngLat(vo.getId(), gdPolyline, lng, lat);
        }

    }

}
