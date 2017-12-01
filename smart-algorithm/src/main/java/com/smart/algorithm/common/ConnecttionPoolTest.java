package com.smart.algorithm.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * MySQL 连接池测试
 * Created by fc.w on 2017/11/28.
 */
public class ConnecttionPoolTest {

    public static void main(String[] args) {
        // 创建数据库连接库对象
        MySqlConnectionPool connPool = new MySqlConnectionPool(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://172.16.13.1:3306/fireeye", "root", "123456");
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // 新建数据库连接库
            connPool.createPool();

            // SQL测试语句
            String sql = "SELECT * FROM dic_area_new limit 10";

            // 从连接库中获取一个可用的连接
            conn = connPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int name = rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            connPool.returnConnection(conn); // 连接使用完后释放连接到连接池
        }


    }

}
