package com.smart.algorithm.pipline;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * Created by  fc.w on 2016/12/5.
 */
public class QianlimaPipeline implements Pipeline {

    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if (entry.getKey().startsWith("resultPage")) {
                JSONArray results = JSON.parseArray(entry.getValue().toString());
                for (int j = 0; j < results.size(); j++) {
                    JSONObject obj = results.getJSONObject(j);
                    System.out.println(obj.getString("xmlb") + "\t" + obj.getString("updatetime") + "\t" + obj.getString("title1") + "\t"+ obj.getLong("cid")
                            +"\t"+ obj.getString("yz") + "\t" + obj.getString("url") + "\t" + obj.getString("ytitle1") + "\t"+ obj.getString("title")
                            +"\t"+ obj.getString("zx") +"\t"+ obj.getString("jzjd") +"\t" + obj.getString("ytitle") + "\t" + obj.getString("genjin")
                            + "\t" + obj.getString("tzje") +"\t"+ obj.getString("yzdw") +"\t"+ obj.getString("diqu") +"\t"+ obj.getInteger("jpxm"));
                }

            } else {
                System.out.println(entry.getKey() +":"+ entry.getValue());
            }

        }
    }
}
