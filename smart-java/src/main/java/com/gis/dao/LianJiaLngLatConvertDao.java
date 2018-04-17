package com.gis.dao;

import com.gis.tools.MySqlConnectionPool;
import com.gis.vo.LianJiaVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fc.w on 2017/12/5.
 */
public class LianJiaLngLatConvertDao {

    /**
     * 百度转高度经纬度
     * @param id
     * @param gdPolyline
     * @param lng
     * @param lat
     */
    public void updateLngLat(int id, String gdPolyline, float lng, float lat) {
        // 创建数据库连接库对象
        MySqlConnectionPool connPool = new MySqlConnectionPool();
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            connPool.createPool();
            String sql = "UPDATE dict_district_lianjia SET gdPolyline = ?, gdLng = ?, gdLat = ? WHERE id = ? ";
            conn = connPool.getConnection();
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            pstmt.setString(1, gdPolyline);
            pstmt.setFloat(2, lng);
            pstmt.setFloat(3, lat);
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
     * 获取边界
     * @return
     */
    public List<LianJiaVO> getPolyline() {
        // 创建数据库连接库对象
        MySqlConnectionPool connPool = new MySqlConnectionPool();
        Connection conn = null;
        PreparedStatement pstmt = null;
        List<LianJiaVO> result = new ArrayList<LianJiaVO>();

        try {
            connPool.createPool();
            conn = connPool.getConnection();
            String sql = "SELECT id, polyline, lng, lat FROM dict_district_lianjia";
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                LianJiaVO vo = new LianJiaVO();
                int id = resultSet.getInt("id");
                String polyline = resultSet.getString("polyline");
                float lng = resultSet.getFloat("lng");
                float lat = resultSet.getFloat("lat");
                vo.setId(id);
                vo.setPolyline(polyline);
                vo.setLng(lng);
                vo.setLat(lat);

                result.add(vo);
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
