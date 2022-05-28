package com.itheima.stock.service;

import com.itheima.stock.common.domain.*;
import com.itheima.stock.vo.resp.PageResult;
import com.itheima.stock.vo.resp.R;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description stock业务层接口
 * @Author Harry
 * @Date 5/23/2022 7:59 PM
 * @Version 1.0
 **/

public interface StockService {

//    List<StockBusiness> getAllBusiness();

    R<List<InnerMarketDomain>> InnerIndexAll();

    R<List<StockBlockDomain>> sectorAllLimit();

    R<List<StockUpDownDomain>> stockIncreaseLimit();

    R<PageResult<StockUpDownDomain>> stockPage(Integer page, Integer pageSize);

    /**
     * Description：沪深两市涨跌停分时行情数据查询，查询T日每分钟的涨跌停数据（T：当前股票交易日）
     * 查询每分钟的涨停和跌停的数据的同级；
     * 如果不在股票的交易日内，那么就统计最近的股票交易下的数据
     * map:
     * upList:涨停数据统计
     * downList:跌停数据统计
     *
     * @return
     */
    R<Map> upDownCount();

    void stockExport(HttpServletResponse response, Integer page, Integer pageSize);

    /**
     * 功能描述：统计国内A股大盘T日和T-1日成交量对比功能（成交量为沪市和深市成交量之和）
     * map结构示例：<Map>
     * {
     * "volList": [{"count": 3926392,"time": "202112310930"},......],
     * "yesVolList":[{"count": 3926392,"time": "202112310930"},......]
     * }</Map>
     *
     * @return
     */
    R<Map> stockTradeVol4InnerMarket();

    /**
     * 个股涨跌幅统计，当前时间
     * 如果当前时间不是交易日，则选取最近的上一个交易时间作为查询时间
     *
     * @return R
     */
    R<Map> stockUpDown();


    /**
     * 功能描述：查询单个个股的分时行情数据，也就是统计指定股票T日每分钟的交易数据；
     * 如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询时间点
     *
     * @param code 股票编码
     */
    R<List<Stock4MinDomain>> stockScreenTimeSharing(String code);

    R<List<StockDailyDKLineDomain>> getDailyKLineData(String stockCode);
}
