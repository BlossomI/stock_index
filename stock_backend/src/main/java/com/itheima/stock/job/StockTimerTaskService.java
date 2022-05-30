package com.itheima.stock.job;

/**
 * @Description 定义股票数据定时采集任务接口
 * @Author Harry
 * @Date 5/29/2022 5:04 PM
 * @Version 1.0
 **/
public interface StockTimerTaskService {

    /*
     * 获取国内大盘数据
     */
    void getInnerMarketInfo();

    /*
     * 获取分钟级A股个股数据
     */
    void getStockRtIndex();

    /*
     * 获取板块数据
     */
    void getStockSectorRtIndex();
}
