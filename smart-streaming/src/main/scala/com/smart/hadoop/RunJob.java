package com.smart.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by fc.w on 2017/6/15.
 */
public class RunJob {

    public static void main(String[] args) throws Exception {
        System.setProperty("hadoop.home.dir", "D:\\hadoop-2.6.1");

        Configuration config = new Configuration();
        // 设置hdfs的通讯地址
        config.set("fs.defaultFS", "hdfs://h1:8020");
        // 设置RN的主机
        config.set("yarn.resourcemanager.hostname", "h1");
        // 加载log4j
        String path = RunJob.class.getResource("/").getPath();
        PropertyConfigurator.configure (path + "log4j.properties");

        try {
            FileSystem fs = FileSystem.get(config);

            Job job = Job.getInstance(config);
            job.setJarByClass(RunJob.class);

            job.setJobName("wc");

            job.setMapperClass(WcMapper.class);
            job.setReducerClass(WcReducer.class);

            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);

            FileInputFormat.addInputPath(job, new Path("/user/root/data.txt"));

            Path outpath = new Path("/user/root/output");
            if (fs.exists(outpath)) {
                fs.delete(outpath, true);
            }
            FileOutputFormat.setOutputPath(job, outpath);

            boolean f = job.waitForCompletion(true);
            if (f) {
                System.out.println("job任务执行成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
