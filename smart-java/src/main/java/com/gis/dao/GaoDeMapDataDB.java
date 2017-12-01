package com.gis.dao;

import com.gis.tools.MySqlConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 高德
 * DB操作
 * Created by fc.w on 2017/11/28.
 */
public class GaoDeMapDataDB {

    /**
     * 更新边界
     * @param name
     * @param polyline
     */
    public void updatePolyline(String name, String polyline) {
        // 创建数据库连接库对象
        MySqlConnectionPool connPool = new MySqlConnectionPool();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            connPool.createPool();
            String sql = "UPDATE dict_district  SET `polyline` = ? WHERE `name` = ? ";
            conn = connPool.getConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            pstmt.setString(1, polyline);
            pstmt.setString(2, name);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            connPool.returnConnection(conn); // 连接使用完后释放连接到连接池
        }

    }

    /**
     * 获取城市信息
     * @return
     */
   public Map<Integer, String> getCityData() {
       Map<Integer, String> names = new HashMap<Integer, String>();
        // 创建数据库连接库对象
        MySqlConnectionPool connPool = new MySqlConnectionPool();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            connPool.createPool();
            String sql = "SELECT id, `name` FROM dict_district WHERE `level` = 3";
            conn = connPool.getConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet results = pstmt.executeQuery();
            while (results.next()) {
                names.put(results.getInt("id"), results.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            connPool.returnConnection(conn); // 连接使用完后释放连接到连接池
        }

        return names;
    }

    /**
     * 获取省，市，区 name
     * @return
     */
   public List<String> getData() {
        List<String> nameList = new ArrayList<String>();
        // 创建数据库连接库对象
        MySqlConnectionPool connPool = new MySqlConnectionPool();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            connPool.createPool();
            String sql = "SELECT `name` FROM dict_district WHERE `level` in (2, 3, 4) AND polyline IS  NULL ";
            conn = connPool.getConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet results = pstmt.executeQuery();
            while (results.next()) {
                nameList.add(results.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            connPool.returnConnection(conn); // 连接使用完后释放连接到连接池
        }

        return nameList;
    }

    /**
     * 添加行政区数据
     * @param cityCode
     * @param adCode
     * @param name
     * @param level
     * @param lon
     */
   public void addData(String cityCode, String adCode, String name, int level, float lon, float lat) {
        // 创建数据库连接库对象
        MySqlConnectionPool connPool = new MySqlConnectionPool();

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            connPool.createPool();
            String sql = "INSERT INTO dict_district(adCode, `name`, cityCode, `level`, lon, lat, createTime) VALUE (?, ?, ?, ?, ?, ?, ?)";
            conn = connPool.getConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            pstmt.setString(1, adCode);
            pstmt.setString(2, name);
            pstmt.setString(3, cityCode);
            pstmt.setInt(4, level);
            pstmt.setFloat(5, lon);
            pstmt.setFloat(6, lat);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = formatter.format(new Date());
            Date date = formatter.parse(dateStr);
            pstmt.setTimestamp(7, new Timestamp(date.getTime()));

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            connPool.returnConnection(conn); // 连接使用完后释放连接到连接池
        }

    }

}
