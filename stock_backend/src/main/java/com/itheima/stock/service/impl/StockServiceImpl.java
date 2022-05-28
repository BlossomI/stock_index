package com.itheima.stock.service.impl;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.stock.common.domain.*;
import com.itheima.stock.common.enums.ResponseCode;
import com.itheima.stock.mapper.StockBlockRtInfoMapper;
import com.itheima.stock.mapper.StockMarketIndexInfoMapper;
import com.itheima.stock.mapper.StockRtInfoMapper;
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
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

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
    public R<Map> upDownCount() {

        // 1 获取股票最近的有效交易日期
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());

        Date curDate = curDateTime.toDate();

        // 开盘日期
        Date openDate = DateTimeUtil.getOpenDate(curDateTime).toDate();

        // todo mock data
        String curTimeStr = "20220106142500";

        // 对应开票日期mock
        String openTimeStr = "20220106092500";
        curDate = DateTime.parse(curTimeStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();

        openDate = DateTime.parse(openTimeStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();


//        统计涨跌停的数据， 约定 1 涨停，0跌停
        List<Map> upList = stockRtInfoMapper.upDownCount(curDate, openDate, 1);

        System.out.println(upList);

        List<Map> downList = stockRtInfoMapper.upDownCount(curDate, openDate, 0);

        System.out.println(downList);

        HashMap<String, List<Map>> result = new HashMap();
        result.put("upList", upList);
        result.put("downList", downList);

        return R.ok(result);

    }

    /**
     * 将指定页的股票数据导出到excel表下
     *
     * @param response
     * @param page     当前页
     * @param pageSize 每页大小
     */
    @Override
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) {

        try {
            // 1. 设置响应数据的类型
            response.setContentType("application/vnd.ms-excel");
            //2.设置响应数据的编码格式
            response.setCharacterEncoding("utf-8");
            //3.设置默认的文件名称
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("stockRt", "UTF-8");
            //设置默认文件名称
            response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xlsx");

            // 分页查询全部数据
            PageHelper.startPage(page, pageSize);

            List<StockUpDownDomain> infos = stockRtInfoMapper.stockAll();
            // 4. 导出到输出流
            List<StockExcelDomain> resultList = infos.stream().map(info -> {
                StockExcelDomain excelDm = new StockExcelDomain();

                BeanUtils.copyProperties(info, excelDm);

                return excelDm;

            }).collect(Collectors.toList());


            EasyExcel.write(response.getOutputStream(), StockExcelDomain.class)
                    .sheet().doWrite(resultList);

//            PageInfo<StockUpDownDomain> infoList = new PageInfo<>(infos);
        } catch (Exception e) {
//            log.info()
            e.printStackTrace();
        }


    }

    /**
     * Description: get the latest trade time and previous days trade time and turn them to Date Object,
     * then query db by them. Finally, combine them to a map;
     *
     * @return result R
     */
    @Override
    public R<Map> stockTradeVol4InnerMarket() {

        //<editor-fold desc="Real Time, tTime, tOpenTIme, preTime, preOpenTIme">
        //1.获取最近的股票交易日时间，精确到分钟 T交易日
        DateTime tDate = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date tDateTime = tDate.toDate();
        //对应的开盘时间
        Date tOpenTime = DateTimeUtil.getOpenDate(tDate).toDate();


        //获取T-1交易日
        DateTime preTDate = DateTimeUtil.getPreviousTradingDay(tDate);
        Date preTTime = preTDate.toDate();
        Date preTOpenTime = DateTimeUtil.getOpenDate(preTDate).toDate();
        //</editor-fold>


        //<editor-fold desc="Mock data">
        //TODO 后续注释掉 mock-data
        String tDateStr = "20220103143000";

        tDateTime = DateTime.parse(tDateStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        //mock-data
        String openDateStr = "20220103093000";
        tOpenTime = DateTime.parse(openDateStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();


        //TODO 后续注释掉 mock-data
        String preTStr = "20220102143000";
        preTTime = DateTime.parse(preTStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();

        //mock-data
        String openDateStr2 = "20220102093000";
        preTOpenTime = DateTime.parse(openDateStr2, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        //</editor-fold>


        // query database Tday
        List<Map> tList = stockRtInfoMapper.stockTradeVolCount(stockInfoConfig.getInner(), tOpenTime, tDateTime);

        // query database T-1 day
        List<Map> preList = stockRtInfoMapper.stockTradeVolCount(stockInfoConfig.getInner(), preTOpenTime, preTTime);

        HashMap<String, List> infos = new HashMap<>();
        infos.put("volList", tList);
        infos.put("yesVolList", preList);


        return R.ok(infos);
    }

    /**
     * 功能描述：统计在当前时间下（精确到分钟），股票在各个涨跌区间的数量
     * 如果当前不在股票有效时间内，则以最近的一个有效股票交易时间作为查询时间点；
     *
     * @return 响应数据格式：
     * {
     * "code": 1,
     * "data": {
     * "time": "2021-12-31 14:58:00",
     * "infos": [
     * {
     * "count": 17,
     * "title": "-3~0%"
     * },
     * ...
     * ]
     * }
     */
    @Override
    public R<Map> stockUpDown() {
        DateTime dateTIme = DateTimeUtil.getLastDate4Stock(DateTime.now());

        Date avlDate = dateTIme.toDate();

        // mock data
        String mockData = "20220106095500";
        avlDate = DateTime.parse(mockData, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();


        // query database
        List<Map> rowList = stockRtInfoMapper.stockUpDownScopeCount(avlDate);

        for (Map map : rowList) {
            System.out.println(map);
        }

        List<String> upDownRange = stockInfoConfig.getUpDownRange();

        List<Map> mapList = upDownRange.stream().map(key -> {
            Optional<Map> title = rowList.stream().filter(map -> key.equals(map.get("title"))).findFirst();

            Map temp = null;
            if (title.isPresent()) {
                temp = title.get();
            } else {
                temp = new HashMap();
                temp.put("title", key);
                temp.put("count", 0);
            }

            return temp;
        }).collect(Collectors.toList());

        HashMap<String, Object> data = new HashMap<>();
        data.put("time", dateTIme.toString());
        data.put("infos", mapList);

        return R.ok(data);
    }

    @Override
    public R<List<Stock4MinDomain>> stockScreenTimeSharing(String code) {
        // get now time and the latest open time
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());

        // get Date from DateTime
        Date curDate = curDateTime.toDate();

        Date openDate = DateTimeUtil.getOpenDate(curDateTime).toDate();

        //<editor-fold desc="Mock data">
        //TODO 后续删除 mock-data
        String mockDate = "20220106142500";
        curDate = DateTime.parse(mockDate, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        String openDateStr = "20220106093000";
        openDate = DateTime.parse(openDateStr, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        //</editor-fold>

        List<Stock4MinDomain> infos = stockRtInfoMapper.stockScreenTimeSharing(code, openDate, curDate);


        return R.ok(infos);
    }

    @Override
    public R<List<StockDailyDKLineDomain>> getDailyKLineData(String stockCode) {
        //<editor-fold desc="get time data">
        //获取当前日期
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        //当前时间节点
        Date curTime = curDateTime.toDate();
        //前推20
        Date pre20Day = curDateTime.minusDays(20).toDate();

        //TODO 后续删除
        String avlDate = "20220106142500";
        curTime = DateTime.parse(avlDate, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        String openDate = "20220101093000";
        pre20Day = DateTime.parse(openDate, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        //</editor-fold>


        // query data from database;
        List<StockDailyDKLineDomain> infos = stockRtInfoMapper.stockScreenDkLine(stockCode, pre20Day, curTime);

        return R.ok(infos);
    }

    //    @Override
//    public List<StockBusiness> getAllBusiness() {
//
//        return stockBusinessMapper.getAll();
//    }
}
