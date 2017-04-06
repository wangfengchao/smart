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
    public void execute(JobExecutionContext jobExecutionContext)  {
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

            Set<String> appNames = appInfos.keySet();
            // 查询所有APPName
            DataBase base = new DataBase();
            Set<String> appNameSet = base.getAllAppNameFunc();
            System.out.println(appNameSet);
            for (String appName : appNames) {
                if (!appNameSet.contains(appName) && appNames.size() <= appNameSet.size()) {
                    FoxmailUtils.sendEmail(appInfos.get(appName), appName);
                } else {
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
        String resAppId = base.updateAppIdFunc(appName);
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
