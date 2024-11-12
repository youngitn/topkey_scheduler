package com.topkey.scheduler.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.topkey.scheduler.erp.quartz.TestQ;
import com.topkey.scheduler.oa.quartz.TestJob;

@Configuration
public class QuartzConfigration {

//    @Bean(name = "scheduler")
//    public SchedulerFactoryBean schedulerFactory(Trigger... triggers) {
//        SchedulerFactoryBean bean = new SchedulerFactoryBean();
//        // 用於quartz集群,QuartzScheduler 啓動時更新己存在的Job
//        bean.setOverwriteExistingJobs(true);
//        // 延時啓動，應用啓動1秒後
//        bean.setStartupDelay(1);
//        // 註冊觸發器
//        bean.setTriggers(triggers);
//        return bean;
//    }
//
//    // -------------  -------------\\
//    @Bean(name = "sayHelloJobDetail") // 指定 jobDetail 名稱
//    public MethodInvokingJobDetailFactoryBean jobDetail(TestQ task) {
//        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
//        jobDetail.setConcurrent(false);
//        jobDetail.setTargetObject(task);
//        jobDetail.setTargetMethod("sayHello");  // 對應上支程式要執行的 method
//        return jobDetail;
//    }
//    
//    @Bean
//    public CronTriggerFactoryBean jobTrigger(JobDetail sayHelloJobDetail) { 
//        String cron = "0,30 * * * * ? *"; // 排程設定
//        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
//        trigger.setJobDetail(sayHelloJobDetail);  // 對應要執行的 jobDetail 名稱
//        trigger.setCronExpression(cron);
//        return trigger;
//    }
//    
    // 用withIdentity建立job id
    @Bean
    JobDetail testQuartz1() {
		return JobBuilder.newJob(TestJob.class).withIdentity("testTask1").storeDurably().build();
	}

    // 用withIdentity建立job id
    @Bean
    JobDetail testQuartz2() {
		return JobBuilder.newJob(TestQ.class).withIdentity("testTask2").storeDurably().build();
	}

    // 用job id來註冊排程
    @Bean
    Trigger testQuartzTrigger1() {
		// 5 sec
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5)
				.repeatForever();
		return TriggerBuilder.newTrigger().forJob("testTask1").withSchedule(scheduleBuilder).build();
	}

    // 用job id來註冊排程
    @Bean
    Trigger testQuartzTrigger2() {
		// 5 sec
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10)
				.repeatForever();
		return TriggerBuilder.newTrigger().forJob("testTask2").withSchedule(scheduleBuilder).build();
	}

    // 用job id來註冊排程 
    @Bean
    Trigger testQuartzTrigger3() {
		// 使用 cron 表達式設定每天12點執行
		String cronExpression = "0 0 12 * * ?"; // 每天12:00執行

		return TriggerBuilder.newTrigger().forJob("testTask2") // 指定要觸發的任務
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)) // 使用 Cron 表達式
				.build();
	}

}