package com.topkey.scheduler.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.topkey.scheduler.oa.quartz.VnAccountRecordJob;

@Configuration
public class QuartzConfigration {
	//越南權限提醒時間參數
	@Value("${vn.sendtime.vnaccount}")
	private String vnAccountRecordTaskCronExpression;

	/**
	 * TKVN帳號權限管理-自動檢測到期名單排程
	 */
	@Bean
	JobDetail Quartz_OAVnAccountRecord() {
		return JobBuilder.newJob(VnAccountRecordJob.class).withIdentity("VnAccountRecordTask").storeDurably().build();
	}

	// 用job id來註冊排程
	@Bean
	Trigger QuartzTrigger_OAVnAccountRecord() {
		// 使用 cron 表達式設定每天8點執行
		String cronExpression = vnAccountRecordTaskCronExpression; // 每天早上08:00執行
		return TriggerBuilder.newTrigger().forJob("VnAccountRecordTask") // 指定要觸發的任務
				.withSchedule(CronScheduleBuilder.cronSchedule(vnAccountRecordTaskCronExpression)) // 使用 Cron 表達式
				.build();
	}
	
    // 用job id來註冊排程
//    @Bean
//    Trigger testQuartzTrigger1() {
//        // 5 sec
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5)
//                .repeatForever();
//        return TriggerBuilder.newTrigger().forJob("VnAccountRecordTask").withSchedule(scheduleBuilder).build();
//    }

}