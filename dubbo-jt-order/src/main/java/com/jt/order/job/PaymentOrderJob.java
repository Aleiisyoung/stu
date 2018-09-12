package com.jt.order.job;

import java.util.Date;

import org.joda.time.DateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.jt.order.mapper.OrderMapper;

public class PaymentOrderJob extends QuartzJobBean{
	//重写方法定义任务的执行
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//Job容器 获取spring容器,执行spring中的任务
		ApplicationContext applicationContext =
				(ApplicationContext) context.getJobDetail()
				.getJobDataMap().get("applicationContext");
		
		//将超时的订单状态由1改为6(定时任务)
		OrderMapper orderMapper = 
				applicationContext.getBean(OrderMapper.class);
		
		//sql 规定2天超时API
		Date dataAgo=new DateTime().minusDays(2).toDate();
		orderMapper.updateStatus(dataAgo);
		System.out.println("定时任务执行完成");
	}
	
	
}
