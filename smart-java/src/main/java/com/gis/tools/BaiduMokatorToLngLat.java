package com.gis.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 百度
 * 墨卡托坐标与经纬度坐标转换
 * Created by fc.w on 2017/11/30.
 */
public class BaiduMokatorToLngLat {

    private static Double EARTHRADIUS = 6370996.81;
    private static Double[] MCBAND = {12890594.86, 8362377.87, 5591021d, 3481989.83, 1678043.12, 0d};
    private static Double[] LLBAND = {75d, 60d, 45d, 30d, 15d, 0d};
    private static Double[][] MC2LL = {{1.410526172116255e-8, 0.00000898305509648872, -1.9939833816331, 200.9824383106796, -187.2403703815547, 91.6087516669843, -23.38765649603339, 2.57121317296198, -0.03801003308653, 17337981.2}, {-7.435856389565537e-9, 0.000008983055097726239, -0.78625201886289, 96.32687599759846, -1.85204757529826, -59.36935905485877, 47.40033549296737, -16.50741931063887, 2.28786674699375, 10260144.86}, {-3.030883460898826e-8, 0.00000898305509983578, 0.30071316287616, 59.74293618442277, 7.357984074871, -25.38371002664745, 13.45380521110908, -3.29883767235584, 0.32710905363475, 6856817.37}, {-1.981981304930552e-8, 0.000008983055099779535, 0.03278182852591, 40.31678527705744, 0.65659298677277, -4.44255534477492, 0.85341911805263, 0.12923347998204, -0.04625736007561, 4482777.06}, {3.09191371068437e-9, 0.000008983055096812155, 0.00006995724062, 23.10934304144901, -0.00023663490511, -0.6321817810242, -0.00663494467273, 0.03430082397953, -0.00466043876332, 2555164.4}, {2.890871144776878e-9, 0.000008983055095805407, -3.068298e-8, 7.47137025468032, -0.00000353937994, -0.02145144861037, -0.00001234426596, 0.00010322952773, -0.00000323890364, 826088.5}};
    private static Double[][] LL2MC = {{-0.0015702102444, 111320.7020616939, 1704480524535203d, -10338987376042340d, 26112667856603880d, -35149669176653700d, 26595700718403920d, -10725012454188240d, 1800819912950474d, 82.5}, {0.0008277824516172526, 111320.7020463578, 647795574.6671607, -4082003173.641316, 10774905663.51142, -15171875531.51559, 12053065338.62167, -5124939663.577472, 913311935.9512032, 67.5}, {0.00337398766765, 111320.7020202162, 4481351.045890365, -23393751.19931662, 79682215.47186455, -115964993.2797253, 97236711.15602145, -43661946.33752821, 8477230.501135234, 52.5}, {0.00220636496208, 111320.7020209128, 51751.86112841131, 3796837.749470245, 992013.7397791013, -1221952.21711287, 1340652.697009075, -620943.6990984312, 144416.9293806241, 37.5}, {-0.0003441963504368392, 111320.7020576856, 278.2353980772752, 2485758.690035394, 6070.750963243378, 54821.18345352118, 9540.606633304236, -2710.55326746645, 1405.483844121726, 22.5}, {-0.0003218135878613132, 111320.7020701615, 0.00369383431289, 823725.6402795718, 0.46104986909093, 2351.343141331292, 1.58060784298199, 8.77738589078284, 0.37238884252424, 7.45}};

    public static void main(String[] args) {
//        Map<String, Double> location = convertMC2LL(13372307.47, 3514145.00);
//        location = convertLL2MC(location.get("lng"),location.get("lat"));
//        System.out.println(location);

        String geo = "4|13517816.2,3597097.8;13527673.6,3607970.0|1-13520242.7,3597248.8,13520242.7,3597399.8,13519636.1,3597399.8,13519636.1,3598607.8,13519484.4,3598607.8,13519484.4,3598758.8,13519029.5,3598758.8,13519029.5,3599060.8,13518574.5,3599060.8,13518574.5,3599211.8,13518271.2,3599211.8,13518271.2,3599815.9,13517816.2,3599815.9,13517816.2,3600268.9,13518119.6,3600268.9,13518119.6,3600570.9,13518271.2,3600570.9,13518271.2,3600872.9,13518574.5,3600872.9,13518574.5,3601023.9,13519181.1,3601023.9,13519181.1,3602533.9,13518877.8,3602533.9,13518877.8,3602986.9,13519332.8,3602986.9,13519332.8,3605100.9,13518574.5,3605100.9,13518574.5,3606611.0,13518422.8,3606611.0,13518422.8,3607064.0,13518574.5,3607064.0,13518574.5,3607366.0,13518877.8,3607366.0,13518877.8,3607819.0,13519332.8,3607819.0,13519332.8,3607970.0,13519787.7,3607970.0,13519787.7,3607064.0,13521152.6,3607064.0,13521152.6,3606460.0,13522062.5,3606460.0,13522062.5,3605856.0,13521910.8,3605856.0,13521910.8,3604345.9,13522669.1,3604345.9,13522669.1,3603288.9,13523730.7,3603288.9,13523730.7,3602533.9,13524185.6,3602533.9,13524185.6,3601023.9,13524337.3,3601023.9,13524337.3,3600721.9,13526157.1,3600721.9,13526157.1,3601174.9,13527218.6,3601174.9,13527218.6,3600419.9,13527370.3,3600419.9,13527370.3,3599513.9,13527673.6,3599513.9,13527673.6,3598758.8,13527218.6,3598758.8,13527218.6,3598305.8,13526612.0,3598305.8,13526612.0,3597097.8,13524943.9,3597097.8,13524943.9,3597550.8,13524792.2,3597550.8,13524792.2,3598154.8,13522517.4,3598154.8,13522517.4,3597701.8,13522214.1,3597701.8,13522214.1,3597550.8,13520697.6,3597550.8,13520697.6,3597097.8,13520242.7,3597097.8;";
        List<String> mocatorList = parseJeo(geo);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mocatorList.size(); i++) {
            String[] coordinate = mocatorList.get(i).split("\\#");
            Map<String, Double> location = convertMC2LL(Double.parseDouble(coordinate[0]), Double.parseDouble(coordinate[1]));
            Double lng = location.get("lng");
            Double lat = location.get("lat");
            String coord = lng+","+lat;
            sb.append(coord);
            if (i < mocatorList.size() - 1) {
                sb.append(";");
            }
        }

    }

    /**
     * 解析Jeo数据
     * @param mocator
     */
    public static List<String> parseJeo(String mocator) {
        List<String> mocatorList = new ArrayList<String>();

        if (null == mocator) return null;
        /* 拆分数据 */
        String[] geos = mocator.split("\\|");
        int n = Integer.parseInt(geos[0]);
        String center = geos[1];
        String polylineMoca = geos[2]; //墨卡托坐标
        String[] plm = polylineMoca.split("\\;");

        /* 获取墨卡托边界 */
        String geo = null;
        if (n == 4) {
            for (int i = 0; i < plm.length; i++) {
                String[] geoPaths = plm[i].split("\\-");
                if (geoPaths[0].equals("1")) {
                    geo = geoPaths[1];
                }
            }
        }
        // 墨卡托坐标解析
        String[] geoPolyline = geo.split("\\,");
        for (int i = 0; i < geoPolyline.length; i += 2) {
            mocatorList.add(geoPolyline[i] + "#" + geoPolyline[i + 1]);
        }

        return mocatorList;
    }

    /**
     * 墨卡托坐标转经纬度坐标
     * @param x
     * @param y
     * @return
     */
    public static Map<String, Double> convertMC2LL(Double x, Double y) {
        Double[] cF = null;
        x = Math.abs(x);
        y = Math.abs(y);
        for (int cE = 0; cE < MCBAND.length; cE++) {
            if (y >= MCBAND[cE]) {
                cF = MC2LL[cE];
                break;
            }
        }
        Map<String,Double> location = converter(x, y, cF);
        location.put("lng",location.get("x"));
        location.remove("x");
        location.put("lat",location.get("y"));
        location.remove("y");
        return location;
    }

    /**
     * 经纬度坐标转墨卡托坐标
     * @param lng
     * @param lat
     * @return
     */
    private static Map<String, Double> convertLL2MC(Double lng, Double lat) {
        Double[] cE = null;
        lng = getLoop(lng, -180, 180);
        lat = getRange(lat, -74, 74);
        for (int i = 0; i < LLBAND.length; i++) {
            if (lat >= LLBAND[i]) {
                cE = LL2MC[i];
                break;
            }
        }
        if (cE!=null) {
            for (int i = LLBAND.length - 1; i >= 0; i--) {
                if (lat <= -LLBAND[i]) {
                    cE = LL2MC[i];
                    break;
                }
            }
        }
        return converter(lng,lat, cE);
    }
    private static Map<String, Double> converter(Double x, Double y, Double[] cE) {
        Double xTemp = cE[0] + cE[1] * Math.abs(x);
        Double cC = Math.abs(y) / cE[9];
        Double yTemp = cE[2] + cE[3] * cC + cE[4] * cC * cC + cE[5] * cC * cC * cC + cE[6] * cC * cC * cC * cC + cE[7] * cC * cC * cC * cC * cC + cE[8] * cC * cC * cC * cC * cC * cC;
        xTemp *= (x < 0 ? -1 : 1);
        yTemp *= (y < 0 ? -1 : 1);
        Map<String, Double> location = new HashMap<String, Double>();
        location.put("x", xTemp);
        location.put("y", yTemp);
        return location;
    }
    private static Double getLoop(Double lng, Integer min, Integer max) {
        while (lng > max) {
            lng -= max - min;
        }
        while (lng < min) {
            lng += max - min;
        }
        return lng;
    }
    private static Double getRange(Double lat, Integer min, Integer max) {
        if (min != null) {
            lat = Math.max(lat, min);
        }
        if (max != null) {
            lat = Math.min(lat, max);
        }
        return lat;
    }

}
