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
import java.util.List;

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

    @GetMapping("/stock/all")
    public R<PageResult<StockUpDownDomain>> stockPage(Integer page, Integer pageSize) {
        return stockService.stockPage(page, pageSize);
    }

    @GetMapping("/updown/count")
    public R<map> upDownCount(){
        retuen stockService.upDownCount();
    }

}
