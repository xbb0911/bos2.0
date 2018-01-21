package com.xbb.bos.quartz;

import com.xbb.bos.service.take_delivery.IWayBillService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据库和索引库定时同步
 * Created by xbb on 2018/1/17.
 */
public class ElasticsearchJob implements Job{

    @Autowired
    private IWayBillService wayBillService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("数据库和索引库同步...");
        //同步索引库和数据库数据
        wayBillService.syncIndex();
    }
}
