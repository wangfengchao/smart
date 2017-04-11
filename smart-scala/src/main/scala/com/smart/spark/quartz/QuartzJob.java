package com.smart.spark.quartz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smart.spark.base.DataBase;
import com.smart.spark.email.FoxmailUtils;
import com.smart.spark.utils.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Quartz事件类
 * Created by fc.w on 2017/3/31.
 */
public class QuartzJob implements Job {

    /**
     * 事件类，用于处理具体业务
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            HttpUtils http = new HttpUtils();
            System.out.println("Testing 1 - Send Http GET request");
            String res = http.sendGet("http://h1:8088/ws/v1/cluster/apps?states=RUNNING");
            JSONObject jsonObj = JSON.parseObject(res);
            JSONArray jsonArray = jsonObj.getJSONObject("apps").getJSONArray("app");
            Map<String, String> appInfos = new HashMap<String, String>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                appInfos.put(obj.getString("name"), obj.getString("id"));
            }

            Set<String> mapKeyAppNames = appInfos.keySet();

            DataBase base = new DataBase();
            Set<String> appNames = base.getAllAppNameFunc();
            System.out.println(appNames);
            for (String appName: mapKeyAppNames) {
                if (! appNames.contains(appName)) {
                    mapKeyAppNames.remove(appName);
                }
            }

            if (mapKeyAppNames.size() < appNames.size()) {
                for (String appName: appNames) {
                    if (!mapKeyAppNames.contains(appName)) {
                        FoxmailUtils.sendEmail(appInfos.get(appName), appName);
                    }
                }
            } else {
                for (String appName : appNames) {
                    process(appInfos.get(appName), appName);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 更新App ID
     * @param appId
     * @param appName
     */
    public void process(String appId, String appName) {
        DataBase base = new DataBase();
        String resAppId = base.selectAppIdFunc(appName);
        if (StringUtils.isNotBlank(resAppId) && !appId.equals(resAppId)) {
            // update app id
            int isSuccess = base.updateAppIdFunc(appId, appName);
            if (isSuccess == 1) {
                System.out.println("-----------Update APP ID Success-----------------------");
            } else {
                System.out.println("-----------Update APP ID Failure-----------------------");
            }
        }

    }
}
