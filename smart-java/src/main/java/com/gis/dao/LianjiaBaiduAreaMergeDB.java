package com.gis.dao;

import com.gis.tools.MySqlConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 链家和百度板块合并
 * Created by fc.w on 2017/12/4.
 */
public class LianjiaBaiduAreaMergeDB {

    /**
     * 更新是否已同步状态
     * @param id
     */
    public void updateStatus(int id) {
        // 创建数据库连接库对象
        MySqlConnectionPool connPool = new MySqlConnectionPool();
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            connPool.createPool();
            String sql = "UPDATE dict_district_lianjia SET syncStatus = 1 WHERE id = ? ";
            conn = connPool.getConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            pstmt.setInt(1, id);
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
     * 获取边界
     * @param cityName
     * @param districtName
     * @return
     */
    public Map<Integer, String> getPolyline(String cityName, String districtName) {
        // 创建数据库连接库对象
        MySqlConnectionPool connPool = new MySqlConnectionPool();
        Connection conn = null;
        PreparedStatement pstmt = null;
        Map<Integer, String> result = new HashMap<Integer, String>();

        try {
            connPool.createPool();
            conn = connPool.getConnection();
            String sql = "SELECT id, polyline FROM dict_district_lianjia WHERE cityName = ? AND `name` = ?";
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            pstmt.setString(1, cityName);
            pstmt.setString(2, districtName);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String polyline = resultSet.getString("polyline");
                result.put(id, polyline);
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

        return result;
    }

    /**
     * 获取城市和板块名称
     * @return
     */
    public Map<String, List<String>> getCityList() {
        // 创建数据库连接库对象
        MySqlConnectionPool connPool = new MySqlConnectionPool();
        Connection conn = null;
        PreparedStatement pstmt = null;
        Map<String, List<String>> result = new HashMap<String, List<String>>();

        try {
            connPool.createPool();
            conn = connPool.getConnection();
            String sql = "SELECT cityName, `name` FROM dict_district_lianjia WHERE status = 1 AND  syncStatus = 0 AND  `level` = 2";
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String cityName = resultSet.getString("cityName");
                String name = resultSet.getString("name");
                List<String> areaNameList = null;
                if (result.containsKey(cityName)) {
                    areaNameList = result.get(cityName);
                    areaNameList.add(name);
                } else {
                    areaNameList = new ArrayList<String>();
                    areaNameList.add(name);
                }
                result.put(cityName, areaNameList);
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

        return result;
    }

}
