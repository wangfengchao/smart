package com.smart.algorithm.gaode.baidu;

import com.smart.algorithm.common.MySqlConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 百度
 * DB操作
 * Created by fc.w on 2017/11/30.
 */
public class DistrictDB {

    /**
     * 获取板块uid
     * @return
     */
    public Map<Integer, String> getUids() {
        // 创建数据库连接库对象
        MySqlConnectionPool connPool = new MySqlConnectionPool();
        Connection conn = null;
        PreparedStatement pstmt = null;
        Map<Integer, String> result = new HashMap<Integer, String>();

        try {
            connPool.createPool();
            conn = connPool.getConnection();
            String sql = "SELECT id, uid FROM  dict_district_baidu WHERE status = 0";
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String uid = resultSet.getString("uid");
                result.put(id, uid);
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
     * 更新板块边界
     * @param id
     * @param geo
     * @param polyline
     * @param gdPolyline
     */
   public void updatePolyline(int id, String geo, String polyline, String gdPolyline) {
        // 创建数据库连接库对象
        MySqlConnectionPool connPool = new MySqlConnectionPool();
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            connPool.createPool();
            String sql = "UPDATE dict_district_baidu  SET geo = ?, `polyline` = ?, gdPolyline = ?, updateTime = sysdate(), status = 1 WHERE id = ? ";
            conn = connPool.getConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            pstmt.setString(1, geo);
            pstmt.setString(2, polyline);
            pstmt.setString(3, gdPolyline);
            pstmt.setInt(4, id);
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
     * 更新没有板块边界点的状态
     * @param id
     */
    public void updateNoPolylineSatatus(int id) {
        // 创建数据库连接库对象
        MySqlConnectionPool connPool = new MySqlConnectionPool();
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            connPool.createPool();
            String sql = "UPDATE dict_district_baidu  SET status = 2 WHERE id = ? ";
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
     * 添加板块数据
     * @param cityId
     * @param cityName
     * @param districtName
     * @param lat
     * @param lng
     * @param address
     * @param uid
     */
    void addDistrictInfo(int cityId, String cityName, String districtName, float lat, float lng, String address, String uid) {
        // 创建数据库连接库对象
        MySqlConnectionPool connPool = new MySqlConnectionPool();
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            connPool.createPool();
            String sql = "INSERT INTO dict_district_baidu(cityId, cityName, districtName, lng, lat, address, uid, createTime) VALUE (?, ?, ?, ?, ?, ?, ?, ?)";
            conn = connPool.getConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            pstmt.setInt(1, cityId);
            pstmt.setString(2, cityName);
            pstmt.setString(3, districtName);
            pstmt.setFloat(4, lng);
            pstmt.setFloat(5, lat);
            pstmt.setString(6, address);
            pstmt.setString(7, uid);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = formatter.format(new Date());
            Date date = formatter.parse(dateStr);
            pstmt.setTimestamp(8, new Timestamp(date.getTime()));

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
