package com.itheima.stock.service.impl;

import com.itheima.stock.mapper.StockBusinessMapper;
import com.itheima.stock.pojo.StockBusiness;
import com.itheima.stock.service.StockService;
import org.springframework.core.metrics.StartupStep;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.security.sasl.SaslServer;
import java.util.List;

/**
 * @Description
 * @Author Harry
 * @Date 5/23/2022 8:05 PM
 * @Version 1.0
 **/
@Service
public class StockServiceImpl implements StockService {

    @Resource
    private StockBusinessMapper stockBusinessMapper;


    @Override
    public List<StockBusiness> getAllBusiness() {

        return stockBusinessMapper.getAll();
    }
}
