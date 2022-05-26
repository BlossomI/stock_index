package com.itheima.stock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.stock.common.domain.InnerMarketDomain;
import com.itheima.stock.common.domain.StockBlockDomain;
import com.itheima.stock.common.domain.StockInfoConfig;
import com.itheima.stock.common.domain.StockUpDownDomain;
import com.itheima.stock.common.enums.ResponseCode;
import com.itheima.stock.mapper.StockBlockRtInfoMapper;
import com.itheima.stock.mapper.StockBusinessMapper;
import com.itheima.stock.mapper.StockMarketIndexInfoMapper;
import com.itheima.stock.mapper.StockRtInfoMapper;
import com.itheima.stock.pojo.StockBlockRtInfo;
import com.itheima.stock.service.StockService;
import com.itheima.stock.util.DateTimeUtil;
import com.itheima.stock.vo.resp.PageResult;
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

    //<editor-fold desc="Mapper bean">
    @Resource
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Resource
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Resource
    private StockRtInfoMapper stockRtInfoMapper;
    //</editor-fold>


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

    /**
     * 沪深两市个股涨幅分时行情数据查询，以时间顺序和涨幅查询前10条数据
     *
     * @return
     */
    @Override
    public R<List<StockUpDownDomain>> stockIncreaseLimit() {

        // 获取当前有效时间
        Date curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();


        // mock数据
        String mockStr = "2021-12-27 09:47:00";

        curDateTime = DateTime.parse(mockStr, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        // 调用mapper接口查询前十的数据，以时间顺序
        List<StockUpDownDomain> infos = stockRtInfoMapper.stockIncreaseLimit(curDateTime);

        if (CollectionUtils.isEmpty(infos)) {
            return R.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }


        return R.ok(infos);
    }

    /**
     * 分页查询涨幅榜数据
     *
     * @param page     当前页
     * @param pageSize 每页显示条目数
     * @return R结果集，data为分页数据，data中rows为数据库查询结果集
     */
    @Override
    public R<PageResult<StockUpDownDomain>> stockPage(Integer page, Integer pageSize) {
        // 设置分页参数
        PageHelper.startPage(page, pageSize);

        // 查询所有数据
        List<StockUpDownDomain> infos = stockRtInfoMapper.stockAll();

        // 将全部数据放到new出来的PageInfo对象中
        PageInfo<StockUpDownDomain> pageInfo = new PageInfo<>(infos);


        // 转换结果集
        PageResult<StockUpDownDomain> pageResult = new PageResult<>(pageInfo);

        return R.ok(pageResult);
    }


    @Override
    public R<map> upDownCount(){
        // 1 获取股票最近的有效交易日期
        
        // 开盘日期
        
        // todo mock data
        String curTimeStr = "20220106142500";
        

    }




    //    @Override
//    public List<StockBusiness> getAllBusiness() {
//
//        return stockBusinessMapper.getAll();
//    }
}
