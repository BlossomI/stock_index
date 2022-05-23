package com.itheima.stock.controller;

import com.itheima.stock.pojo.StockBusiness;
import com.itheima.stock.service.StockService;
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

    @RequestMapping("/stock/business/all")
    public List<StockBusiness> getAllBussiness() {
        return stockService.getAllBusiness();
    }
}
