package com.itheima.stock.job.impl;

import com.itheima.stock.job.StockTimerTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class StockTimerTaskServiceImplTest {

    @Resource
    private StockTimerTaskService stockTimerTaskService;

    @Test
    void getForeignMarketInfo() {
       stockTimerTaskService.getForeignMarketInfo();
    }
}