package com.itheima.stock.common.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Description
 * @Author Harry
 * @Date 5/25/2022 12:52 AM
 * @Version 1.0
 **/
@Data
@ConfigurationProperties(prefix = "stock")
public class StockInfoConfig {
    // a股大盘ID集合
    private List<String> inner;

    // 外盘ID集合
    private List<String> outer;

    //股票区间
    private List<String> upDownRange;
}
