package com.dodoca.smart.pipline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * Created by  fc.w on 2016/12/6.
 */
public class MofcomPipeline implements Pipeline {

    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() +"\t"+ entry.getValue());
        }
    }
}
