package com.smart.spark.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fc.w on 2017/4/1.
 */
public class DataBase {

    /**
     * 获取所有App Id
     * @return
     */
    public List<String> getAllAppNameFunc() {
        List<String> retList = new ArrayList<String>();
//        try {
//            Connection conn = DBUtils.getConnection();
//            PreparedStatement preStmt = conn.prepareStatement("SELECT app_name FROM yarn_app");
//            ResultSet res = preStmt.executeQuery();
//            while (res.next()) {
//                retList.add(res.getString(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
////            DBUtils.closeConnection();
//        }

        return retList;
    }

//    /**
//     * 根据应用名称查询是否有记录
//     * @param appName
//     * @return
//     */
//    public String selectAppIdFunc(String appName) {
//        String resAppId = "";
//        try {
//            Connection conn = DBUtils.getConnection();
//            PreparedStatement preStmt = conn.prepareStatement("SELECT app_id FROM yarn_app WHERE app_name = ?");
//            preStmt.setString(1, appName);
//            ResultSet res = preStmt.executeQuery();
//            while (res.next()) {
//                resAppId = res.getString(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
////            DBUtils.closeConnection();
//        }
//
//        return resAppId;
//    }
//
//    /**
//     * 添加App Id
//     * @param appId
//     * @param appName
//     * @return
//     */
//    public int addAppInfoFunc(String appId, String appName) {
//        int isSuccess = 0;
//        try {
//            Connection conn = DBUtils.getConnection();
//            PreparedStatement preStmt = conn.prepareStatement("INSERT INTO yarn_app(app_id, app_name) values(?, ?)");
//            preStmt.setString(1, appId);
//            preStmt.setString(2, appName);
//            isSuccess = preStmt.executeUpdate();
//            preStmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
////            DBUtils.closeConnection();
//        }
//
//        return isSuccess;
//    }
//
//    /**
//     * 通过应用名称更新App ID
//     * @param appId
//     * @param appName
//     * @return
//     */
//    public int  updateAppIdFunc(String appId, String appName) {
//        int isSuccess = 0;
//        try {
//            Connection conn = DBUtils.getConnection();
//            PreparedStatement preStmt = conn.prepareStatement("UPDATE yarn_app SET app_id = ? WHERE app_name = ?");
//            preStmt.setString(1, appId);
//            preStmt.setString(2, appName);
//            isSuccess = preStmt.executeUpdate();
//            preStmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
////            DBUtils.closeConnection();
//        }
//
//        return isSuccess;
//    }


}
