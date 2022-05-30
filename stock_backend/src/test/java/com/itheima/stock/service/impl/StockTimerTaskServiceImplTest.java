package com.itheima.stock.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class StockTimerTaskServiceImplTest {


    @Resource
    private StockTimerTaskServiceImpl stockTimerTaskService;


    @Test
    void getInnerMarketInfo() {
        stockTimerTaskService.getInnerMarketInfo();
    }

    @Test
    void getStockSectorRtIndex() {
        stockTimerTaskService.getStockSectorRtIndex();
    }
}