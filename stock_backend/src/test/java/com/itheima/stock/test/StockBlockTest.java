package com.itheima.stock.test;

import com.itheima.stock.common.domain.StockBlockDomain;
import com.itheima.stock.common.domain.StockUpDownDomain;
import com.itheima.stock.pojo.StockBlockRtInfo;
import com.itheima.stock.service.StockService;
import com.itheima.stock.util.DateTimeUtil;
import com.itheima.stock.vo.resp.R;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author Harry
 * @Date 5/25/2022 4:14 PM
 * @Version 1.0
 **/
@SpringBootTest
public class StockBlockTest {

    @Resource
    private StockService stockService;


    @Test
    public void test2(){

        R<List<StockUpDownDomain>> listR = stockService.stockIncreaseLimit();

        System.out.println(listR);
    }


    @Test
    public void test1(){

        String mockStr = "2021-12-27 09:47:00";
        // Date 類型
        Date parse1 = DateTime.parse(mockStr, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        System.out.print("Date:      ");

        System.out.println(parse1);

        System.out.println();
        // mock数据

        DateTime parse = DateTime.parse(mockStr, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.print("DateTime： ");

        System.out.println(parse);
    }
}
