package com.itheima.stock.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description
 * @Author Harry
 * @Date 5/28/2022 11:47 AM
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock4MinDomain {

    /**
     * 日期，eg:202201280809
     */
    private String date;

    /**
     * 交易量
     */
    private Long tradeAmt;

    /**
     * 股票编码
     */
    private String code;

    /**
     * 最低价
     */
    private BigDecimal lowPrice;

    /**
     * 前收盘价
     */
    private BigDecimal preClosePrice;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 最高价
     */
    private BigDecimal highPrice;

    /**
     * 开盘价
     */
    private BigDecimal openPrice;


    /**
     * 当前交易总金额
     */
    private BigDecimal tradeVol;

    /**
     * 当前价格
     */
    private BigDecimal tradePrice;

}
