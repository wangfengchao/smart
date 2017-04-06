package com.smart.algorithm.pipline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Created by  fc.w on 2016/12/6.
 */
public class ImgPipeline extends FilePersistentBase implements Pipeline {

    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if (entry.getValue() instanceof List) {
                List listOne = (List) entry.getValue();
                List<String> listUrls = new ArrayList<String>();
                for (int i = 0; i < listOne.size(); i++) {
                    listUrls.add((String)listOne.get(i));
                }
                for (int i = 0; i < listUrls.size(); i++) {
                    BufferedInputStream in = null;
                    BufferedOutputStream out = null;
                    try {
                        in = new BufferedInputStream(new URL(listUrls.get(i)).openStream());
                        String[] imgPaths = listUrls.get(i).split("/");
                        String imgName = imgPaths[imgPaths.length - 1];
                        File file = new File("D://tupian/" + imgName);
                        out = new BufferedOutputStream(new FileOutputStream(file));
                        byte[] data = new byte[2048];
                        int length = in.read(data);
                        while (length != -1) {
                            out.write(data, 0, data.length);
                            length = in.read(data);
                        }
                        System.out.println("正在执行下载任务：当前正在下载图片:" + imgName);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }


            System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }
    }

}
