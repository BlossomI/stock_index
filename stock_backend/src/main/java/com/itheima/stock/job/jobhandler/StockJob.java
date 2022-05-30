package com.itheima.stock.job.jobhandler;

import com.itheima.stock.job.StockTimerTaskService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定义股票相关数据的定时任务
 *
 * @author laofang
 */
@Component
@Slf4j
public class StockJob {

    @XxlJob("hema_job_test")
    public void jobTest() {
        System.out.println("jobTest run.....");
    }


    @Resource
    private StockTimerTaskService stockTimerTaskService;


    /**
     * 定义定时任务，采集国内大盘数据
     */
    @XxlJob("getStockInnerMarketInfos")
    public void getStockInnerMarketInfos() {
        stockTimerTaskService.getInnerMarketInfo();
        System.out.println("国内大盘数据定时采集");
    }

    /**
     * 板块定时任务
     */
    @XxlJob("getStockBlockInfoTask")
    public void getStockBlockInfoTask() {
        stockTimerTaskService.getStockSectorRtIndex();
    }

    /**
     * 定时采集A股数据
     */
    @XxlJob("getStockInfos")
    public void getStockInfos() {
        stockTimerTaskService.getStockRtIndex();
    }

    public void init() {
        log.info("init");
    }

    public void destroy() {
        log.info("destory");
    }
}