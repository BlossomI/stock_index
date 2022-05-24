package com.itheima.stock.common.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 定义封装国内大盘数据的实体类
 * @Author Harry
 * @Date 5/25/2022 12:44 AM
 * @Version 1.0
 **/
@Data
public class InnerMarketDomain {
    /*
     jdbc:bigint--->java:long
    */
    private Long tradeAmt;
    /*
        jdbc:decimal --->java:BigDecimal
     */
    private String code;
    private String name;
    private String curDate;
    private Long tradeVol;
    private BigDecimal upDown;
    private BigDecimal tradePrice;

    private BigDecimal openPrice;
    private BigDecimal preClosePrice;
}
