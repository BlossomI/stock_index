package com.itheima.stock.common.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author Harry
 * @Date 5/25/2022 2:24 PM
 * @Version 1.0
 **/
@Data
@Builder
public class StockBlockDomain {
    /**
     * 公司数量
     */
    private Integer companyNum;
    /**
     * 交易量
     */
    private Long tradeAmt;
    /**
     * 板块编码
     */
    private String code;
    /**
     * 平均价
     */
    private BigDecimal avgPrice;
    /**
     * 板块名称
     */
    private String name;
    /**
     * 当前日期
     */
    private String curDate;
    /**
     * 交易金额
     */
    private BigDecimal tradeVol;
    private String updownRate;
}
