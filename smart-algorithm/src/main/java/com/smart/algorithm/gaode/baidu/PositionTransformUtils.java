package com.smart.algorithm.gaode.baidu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PositionTransformUtils {
    private static double pi = 3.14159265358979324 * 3000.0 / 180.0;

    private static DecimalFormat df = new DecimalFormat( "0.000000" );

    /**
     * 功能描述:国测局经纬度转百度经纬度
     * <p>
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     CK:   2015年4月15日      新建
     * </pre>
     *
     * @param positions 经度维度用","连接的字符串集合，多个以;拼接的字符串
     * @return 转换后的经度维度用","连接的字符串集合
     */
    public static List<String> gcj2bd(List<String> positions) {
        if (positions != null && positions.size() > 0) {
            List<String> resultPositions = new ArrayList<String>( positions.size() );
            for (String position : positions) {
                resultPositions.add( bd2gcj( position ) );
            }
            return resultPositions;
        }
        return null;
    }

    /**
     * 功能描述:国测局经纬度转百度经纬度
     * <p>
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     Yang'ushan:   2015年4月15日      新建
     * </pre>
     *
     * @param positions 经度维度用","连接的字符串集合
     * @return
     */
    public static String gcj2bd_long(String positions) {
        if (positions != null && positions.trim().length() > 0) {
            String[] pArray = positions.split( ";" );
            if (pArray != null && pArray.length > 0) {
                StringBuilder resultPositions = new StringBuilder( "" );
                for (String p : pArray) {
                    String temp = gcj2bd( p );
                    if (temp != null && temp.trim().length() > 0) {
                        resultPositions.append( temp );
                        resultPositions.append( ";" );
                    }
                }
                if (resultPositions.lastIndexOf( ";", resultPositions.length() - 2 ) != -1) {
                    resultPositions.deleteCharAt( resultPositions.length() - 1 );
                }
                return resultPositions.toString();
            }
        }
        return null;
    }


    /**
     * 功能描述:国测局经纬度转百度经纬度
     * <p>
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     Yang'ushan:   2015年4月15日      新建
     * </pre>
     *
     * @param lng 经度
     * @param lat 纬度
     * @return
     */
    public static String gcj2bd(String lng, String lat) {
        String position = lng + "," + lat;
        return gcj2bd( position );
    }

    /**
     * 功能描述:国测局经纬度转百度经纬度
     * <p>
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     CK:   2015年4月15日      新建
     * </pre>
     *
     * @param position 经度维度用","连接的字符串
     * @return 转换后的经度维度用","连接的字符串
     */
    public static String gcj2bd(String position) {
        if (position != null) {
            String[] sarr = position.split( "," );
            double lng = Double.parseDouble( sarr[0].trim() );
            double lat = Double.parseDouble( sarr[1].trim() );
            double a = Math.sqrt( lat * lat + lng * lng ) + 0.00002 * Math.sin( lat * pi );
            double b = Math.atan2( lat, lng ) + 0.000003 * Math.cos( lng * pi );
            return (df.format( a * Math.cos( b ) + 0.0065 )) + "," + (df.format( a * Math.sin( b ) + 0.006 ));
        }
        return null;
    }

    /**
     * 功能描述:百度经纬度转国测局经纬度
     * <p>
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     CK:   2015年4月15日      新建
     * </pre>
     *
     * @param position 经度维度用","连接的字符串
     * @return 转换后的经度维度用","连接的字符串
     */
    public static String bd2gcj(String position) {
        if (position != null) {
            String[] sarr = position.split( "," );
            if (sarr.length < 2) {
                return null;
            }
            double lng = Double.parseDouble( sarr[0].trim() ) - 0.0065;
            double lat = Double.parseDouble( sarr[1].trim() ) - 0.006;
            double a = Math.sqrt( lat * lat + lng * lng ) - 0.00002 * Math.sin( lat * pi );
            double b = Math.atan2( lat, lng ) - 0.000003 * Math.cos( lng * pi );
            return (df.format( a * Math.cos( b ) )) + "," + (df.format( a * Math.sin( b ) ));
        }
        return null;
    }

    /**
     * 功能描述:百度经纬度转国测局经纬度
     * <p>
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     Yang'ushan:   2015年4月15日      新建
     * </pre>
     *
     * @param lng 经度
     * @param lat 纬度
     * @return
     */
    public static String bd2gcj(String lng, String lat) {
        String position = lng + "," + lat;
        return bd2gcj( position );
    }

    /**
     * 功能描述:百度经纬度转国测局经纬度
     * <p>
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     CK:   2015年4月15日      新建
     * </pre>
     *
     * @param positions 经度维度用","连接的字符串集合
     * @return 转换后的经度维度用","连接的字符串集合
     */
    public static List<String> bd2gcj(List<String> positions) {
        if (positions != null && positions.size() > 0) {
            List<String> resultPositions = new ArrayList<String>( positions.size() );
            for (String position : positions) {
                resultPositions.add( gcj2bd( position ) );
            }
            return resultPositions;
        }
        return null;
    }

    /**
     * 功能描述:百度经纬度转国测局经纬度
     * <p>
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     Yang'ushan:   2015年4月15日      新建
     * </pre>
     *
     * @param positions 经度维度用", "连接的字符串集合，多个以;拼接的字符串
     * @return
     */
    public static String bd2gcj_long(String positions) {
        if (positions != null && positions.trim().length() > 0) {
            String[] pArray = positions.split( ";" );
            if (pArray != null && pArray.length > 0) {
                StringBuilder resultPositions = new StringBuilder( "" );
                for (String p : pArray) {
                    p = p.replace( " ", "" ); // 去除空格
                    String temp = bd2gcj( p );
                    if (temp != null && temp.trim().length() > 0) {
                        resultPositions.append( temp );
                        resultPositions.append( ";" );
                    }
                }
                if (resultPositions.lastIndexOf( ";", resultPositions.length() - 2 ) != -1) {
                    resultPositions.deleteCharAt( resultPositions.length() - 1 );
                }
                return resultPositions.toString();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println( gcj2bd( "121.449646,31.275364" ) );
        //121.51951284749504,31.253706715178215
    }
}