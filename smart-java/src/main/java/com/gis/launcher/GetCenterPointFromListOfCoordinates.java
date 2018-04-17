package com.gis.launcher;

import com.gis.vo.GeoCoordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fc.w on 2017/12/8.
 */
public class GetCenterPointFromListOfCoordinates {

    /**
     *  根据输入的地点坐标计算中心点
     * @param geoCoordinateList
     * @return
     */
    public static GeoCoordinate getCenterPoint(List<GeoCoordinate> geoCoordinateList) {
        int total = geoCoordinateList.size();
        double X = 0, Y = 0, Z = 0;
        for (GeoCoordinate g : geoCoordinateList) {
            double lat, lon, x, y, z;
            lat = g.getLatitude() * Math.PI / 180;
            lon = g.getLongitude() * Math.PI / 180;
            x = Math.cos(lat) * Math.cos(lon);
            y = Math.cos(lat) * Math.sin(lon);
            z = Math.sin(lat);
            X += x;
            Y += y;
            Z += z;
        }

        X = X / total;
        Y = Y / total;
        Z = Z / total;
        double Lon = Math.atan2(Y, X);
        double Hyp = Math.sqrt(X * X + Y * Y);
        double Lat = Math.atan2(Z, Hyp);
        return new GeoCoordinate(Lat * 180 / Math.PI, Lon * 180 / Math.PI);
    }

    /**
     * 根据输入的地点坐标计算中心点（适用于400km以下的场合）
     * @param geoCoordinateList
     * @return
     */
    public static GeoCoordinate getCenterPoint400(List<GeoCoordinate> geoCoordinateList) {
        // 以下为简化方法（400km以内）
        int total = geoCoordinateList.size();
        double lat = 0, lon = 0;
        for (GeoCoordinate g : geoCoordinateList) {
            lat += g.getLatitude() * Math.PI / 180;
            lon += g.getLongitude() * Math.PI / 180;
        }
        lat /= total;
        lon /= total;
        return new GeoCoordinate(lat * 180 / Math.PI, lon * 180 / Math.PI);
    }

    public static void main(String[] args) {
        List<GeoCoordinate> geoCoordinateList = new ArrayList<GeoCoordinate>();
        GeoCoordinate g = new GeoCoordinate();
        g.setLongitude(112.977324);
        g.setLatitude(28.178376);
        GeoCoordinate g2 = new GeoCoordinate();
        g2.setLongitude(112.975782);
        g2.setLatitude(28.172258);
        geoCoordinateList.add(g);
        geoCoordinateList.add(g2);

        GeoCoordinate  re = GetCenterPointFromListOfCoordinates.getCenterPoint(geoCoordinateList);
        System.out.println(re.getLongitude() +"   "+ re.getLatitude());
    }


}
