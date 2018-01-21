package com.xbb.bos.quartz;

import com.xbb.bos.service.take_delivery.IPromotionService;
import com.xbb.bos.service.take_delivery.IWayBillService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 定时设置宣传任务状态
 * Created by xbb on 2018/1/8.
 */
public class PromotionJob implements Job{

    @Autowired
    private IPromotionService promotionService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("活动过期处理程序执行...");
        //每分钟执行一次,当前时间大于promotion数据表endDate,活动过期,设置status = "2"
        promotionService.updateStatus(new Date());

    }
}
