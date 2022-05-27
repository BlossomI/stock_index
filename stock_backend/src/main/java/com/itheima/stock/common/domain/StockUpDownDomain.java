package com.itheima.stock.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description 股票涨幅榜Do封装 十个field
 * @Author Harry
 * @Date 5/25/2022 8:12 PM
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockUpDownDomain {
    /**
     * 股票编码
     */
    private String code;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 前收盘价
     */
    private BigDecimal preClosePrice;

    /**
     * 当前价格
     */
    private BigDecimal tradePrice;

    /**
     * 涨跌
     */
    private BigDecimal increase;

    /**
     * 涨幅
     */
    private BigDecimal upDown;

    /**
     * 振幅
     */
    private BigDecimal amplitude;

    /**
     * 交易量
     */
    private Long tradeAmt;

    /**
     * 交易金额
     */
    private BigDecimal tradeVol;

    /**
     * 当前日期
     */
    private String curDate;


}
