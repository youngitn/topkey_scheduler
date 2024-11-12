package com.topkey.scheduler.erp.quartz;

import java.text.SimpleDateFormat;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.topkey.scheduler.erp.repo.SalesOrderRepository;
/**
 * 測試
 * 相關設定在com.topkey.scheduler.config.QuartzConfigration裡面 
 */
@Configuration
@Component
@EnableScheduling
public class TestQ extends QuartzJobBean {

	@Autowired
	private SalesOrderRepository srp;
	
    public void sayHello() {
        
    }

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
        System.out.println("Hello:" + srp.findByUkid("5731").size());
	}
    
}