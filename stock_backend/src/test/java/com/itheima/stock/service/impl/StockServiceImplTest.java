package com.itheima.stock.service.impl;

import com.itheima.stock.common.domain.ForeignMarketDomain;
import com.itheima.stock.service.StockService;
import com.itheima.stock.vo.resp.R;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class StockServiceImplTest {

    @Autowired
    private StockService stockService;

    @Test
    void upDownCount() {
//        R<Map> mapR = stockService.upDownCount();
//
//        System.out.println("mapR = " + mapR);
    }

    @Test
    public void stockUpDownTest() {
//        R<Map> mapR = stockService.stockUpDown();
//
//        Map data = mapR.getData();
//
//        List<Map> maps = (List<Map>) data.get("time");
//
//        for (Map map1 : maps) {
//            System.out.println(map1);
//        }
//
//        String key = "<-7%";
//
//        Optional<Map> t1 = maps.stream().filter(map -> key.equals(map.get("title"))).findFirst();
//
//        System.out.println(t1);


    }

    @Test
    void getForeignMarketInfo() {
        R<List<ForeignMarketDomain>> r = stockService.getForeignMarketInfo();
        for (ForeignMarketDomain datum : r.getData()) {
            System.out.println(datum);
        }
    }
}