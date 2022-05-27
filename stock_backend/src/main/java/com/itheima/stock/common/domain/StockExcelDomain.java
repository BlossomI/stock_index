package com.itheima.stock.common.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description
 * @Author Harry
 * @Date 5/27/2022 12:38 AM
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@HeadRowHeight(value = 35)     // 表头行高
@ContentRowHeight(value = 25)  // 内容行高
@ColumnWidth(value = 50)       // 列宽
public class StockExcelDomain {
    /**
     * 股票编码
     */
    @ExcelProperty(value = {"股票涨幅信息统计表", "股票编码"}, index = 0)
    private String code;

    /**
     * 股票名称
     */
    @ExcelProperty(value = {"股票涨幅信息统计表", "股票名称"}, index = 1)
    private String name;

    /**
     * 前收盘价
     */
    @ExcelProperty(value = {"股票涨幅信息统计表", "前收盘价"}, index = 2)
    private BigDecimal preClosePrice;

    /**
     * 当前价格
     */
    @ExcelProperty(value = {"股票涨幅信息统计表", "当前价格"}, index = 3)
    private BigDecimal tradePrice;

    /**
     * 涨跌
     */
    @ExcelProperty(value = {"股票涨幅信息统计表", "当前价格"}, index = 4)
    private BigDecimal increase;

    /**
     * 涨幅
     */
    @ExcelProperty(value = {"股票涨幅信息统计表", "涨幅"}, index = 5)
    private BigDecimal upDown;

    /**
     * 振幅
     */
    @ExcelProperty(value = {"股票涨幅信息统计表", "振幅"}, index = 6)
    private BigDecimal amplitude;

    /**
     * 交易量
     */
    @ExcelProperty(value = {"股票涨幅信息统计表", "交易量"}, index = 7)
    private Long tradeAmt;

    /**
     * 交易金额
     */
    @ExcelProperty(value = {"股票涨幅信息统计表", "交易金额"}, index = 8)
    private BigDecimal tradeVol;

    /**
     * 当前日期
     */
    @ExcelProperty(value = {"股票涨幅信息统计表", "当前日期"}, index = 9)
    private String curDate;

}
