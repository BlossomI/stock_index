package com.itheima.stock.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description 定义封装国内大盘数据的实体类
 * @Author Harry
 * @Date 5/25/2022 12:44 AM
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForeignMarketDomain {

    /**
     * 当前点数
     */
    private BigDecimal curPoint;

    /**
     * cur_time
     */
    private String curDate;

    /**
     * mark_name
     */
    private String name;

    /**
     * 当前价格
     */
    private BigDecimal curPrice;

    /**
     * updownRate
     */
    private BigDecimal upDown;
}
