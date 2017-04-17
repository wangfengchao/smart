package com.smart.spark.quartz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smart.spark.base.DataBase;
import com.smart.spark.email.FoxmailUtils;
import com.smart.spark.utils.ConfigProperties;
import com.smart.spark.utils.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.*;


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
            String yarnUrl = ConfigProperties.getString("yarn.url");
            String res = http.sendGet(yarnUrl+"/ws/v1/cluster/apps?states=RUNNING");

            // 解析json获取sparkStreaming应用名
            JSONObject jsonObj = JSON.parseObject(res);
            JSONArray jsonArray = jsonObj.getJSONObject("apps").getJSONArray("app");
            List<String> appInfos = new ArrayList<String>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                appInfos.add(obj.getString("name"));
            }

            // 查询DB 获取sparkStreaming应用名
            DataBase dataBase = new DataBase();
            List<String> appNames = dataBase.getAllAppNameFunc();
            for (String appName : appNames) {
                if (! appInfos.contains(appName)) {
                    // 发送异常邮件
                    FoxmailUtils.sendEmail(appName);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
