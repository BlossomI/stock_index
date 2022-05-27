package com.itheima.stock.controller;

import com.itheima.stock.common.domain.InnerMarketDomain;
import com.itheima.stock.common.domain.StockBlockDomain;
import com.itheima.stock.common.domain.StockUpDownDomain;
import com.itheima.stock.pojo.StockBusiness;
import com.itheima.stock.service.StockService;
import com.itheima.stock.vo.resp.PageResult;
import com.itheima.stock.vo.resp.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Harry
 * @Date 5/23/2022 7:56 PM
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/quot")
public class StockController {

    @Resource
    private StockService stockService;


    /**
     * 国内大盘指数模块
     *
     * @return R结果集，包括装有所有大盘信息对象List
     */
    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> innerIndexALl() {

        return stockService.InnerIndexAll();
    }

    /**
     * 国内板块指数模块
     *
     * @return R结果集，包含所有板块对象List
     */
    @GetMapping("/sector/all")
    public R<List<StockBlockDomain>> sectorAll() {
        return stockService.sectorAllLimit();
    }

    /**
     * 沪深两市个股涨幅分时行情数据查询，以时间顺序和涨幅查询前10条数据
     *
     * @return
     */
    @GetMapping("/stock/increase")
    public R<List<StockUpDownDomain>> stockIncreaseLimit() {
        return stockService.stockIncreaseLimit();
    }

    /**
     * 涨幅榜更多信息查看，分页查询
     *
     * @param page     页码
     * @param pageSize 每页条数
     * @return 当前页信息
     */
    @GetMapping("/stock/all")
    public R<PageResult<StockUpDownDomain>> stockPage(Integer page, Integer pageSize) {
        return stockService.stockPage(page, pageSize);
    }

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
    @GetMapping("/updown/count")
    public R<Map> upDownCount() {
        return stockService.upDownCount();
    }

    /**
     * 涨幅榜数据导出
     *
     * @param response 响应对象，直接把excel数据写给相应输出流即可
     * @param page     页码
     * @param pageSize 每页条数
     */
    @GetMapping("/stock/export")
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) {
        stockService.stockExport(response, page, pageSize);
    }

    /**
     * Description：统计国内A股大盘T日和T-1日成交量对比功能（成交量为沪市和深市成交量之和）
     *
     * @return Resule R
     */
    @GetMapping("/stock/tradevol")
    public R<Map> stockTradeVol4InnerMarket() {
        return stockService.stockTradeVol4InnerMarket();
    }

    /**
     * 个股涨跌幅统计，当前时间
     * 如果当前时间不是交易日，则选取最近的上一个交易时间作为查询时间
     *
     * @return result R
     */
    @GetMapping("/stock/updown")
    public R<Map> getStockUpDown(){
        return stockService.stockUpDown();
    }
}
