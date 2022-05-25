package com.itheima.stock.service.impl;

import com.itheima.stock.common.domain.InnerMarketDomain;
import com.itheima.stock.common.domain.StockBlockDomain;
import com.itheima.stock.common.domain.StockInfoConfig;
import com.itheima.stock.common.enums.ResponseCode;
import com.itheima.stock.mapper.StockBlockRtInfoMapper;
import com.itheima.stock.mapper.StockBusinessMapper;
import com.itheima.stock.mapper.StockMarketIndexInfoMapper;
import com.itheima.stock.pojo.StockBlockRtInfo;
import com.itheima.stock.service.StockService;
import com.itheima.stock.util.DateTimeUtil;
import com.itheima.stock.vo.resp.R;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

    @Resource
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;


    @Resource
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;


    @Resource
    private StockInfoConfig stockInfoConfig;


    @Override
    public R<List<InnerMarketDomain>> InnerIndexAll() {
        // 1. 获取国内大盘id集合
        List<String> innerIds = stockInfoConfig.getInner();

        // 2. 获取最新的股票有效交易日
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();

        // mock 数据
        String mockDate = "20211226105600";//TODO后续大盘数据实时拉去，将该行注释掉 传入的日期秒必须为0
        lastDate = DateTime.parse(mockDate, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();

        List<InnerMarketDomain> maps = stockMarketIndexInfoMapper.selectByIdsAndDate(innerIds, lastDate);

        // 組裝數據
        if (CollectionUtils.isEmpty(maps)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }


        return R.ok(maps);
    }

    @Override
//    public R<List<StockBlockDomain>> sectorAllLimit() {
    public R<List<StockBlockDomain>> sectorAllLimit() {
        // 调用mapper获取数据
        List<StockBlockDomain> stockBlockList = stockBlockRtInfoMapper.sectorAllLimit();

        if (CollectionUtils.isEmpty(stockBlockList)) {
            System.out.println("there is no data");
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }





        // 组装数据
        return R.ok(stockBlockList);
    }

//    @Override
//    public List<StockBusiness> getAllBusiness() {
//
//        return stockBusinessMapper.getAll();
//    }
}
