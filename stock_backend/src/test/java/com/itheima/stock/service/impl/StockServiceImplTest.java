package com.itheima.stock.service.impl;

import com.itheima.stock.service.StockService;
import com.itheima.stock.vo.resp.R;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockServiceImplTest {

    @Autowired
    private StockService stockService;

    @Test
    void upDownCount() {
        R<Map> mapR = stockService.upDownCount();

        System.out.println("mapR = " + mapR);
    }
}