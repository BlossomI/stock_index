package com.itheima.stock.controller;

import com.itheima.stock.common.domain.InnerMarketDomain;
import com.itheima.stock.pojo.StockBusiness;
import com.itheima.stock.service.StockService;
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


    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> innerIndexALl(){

        return stockService.InnerIndexAll();
    }


//    @RequestMapping("/stock/business/all")
//    public List<StockBusiness> getAllBussiness() {
//        return stockService.getAllBusiness();
//    }
}
