package com.smart.spark.quartz;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Quartz 定时执行事件
 * Created by fc.w on 2017/3/31.
 */
public class ScheduleRunner {

    public static void main(String[] args) throws Exception {
        ScheduleRunner runner = new ScheduleRunner();
        runner.startSchedule();
    }

    public void startSchedule() throws Exception {
        try {
            // 1、创建一个JobDetail实例，指定Quartz
            JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)
                    .withIdentity("job1_1", "jGroup1")
                    .build();
            //2、创建Trigger
            SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(60)
                    .repeatForever();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(
                    "trigger1_1","tGroup1").startNow().withSchedule(builder).build();
            //3、创建Scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            //4、调度执行
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
