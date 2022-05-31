package com.itheima.stock.job.impl;

import com.google.common.collect.Lists;
import com.itheima.stock.common.domain.StockInfoConfig;
import com.itheima.stock.mapper.StockBlockRtInfoMapper;
import com.itheima.stock.mapper.StockBusinessMapper;
import com.itheima.stock.mapper.StockMarketIndexInfoMapper;
import com.itheima.stock.mapper.StockRtInfoMapper;
import com.itheima.stock.pojo.StockBlockRtInfo;
import com.itheima.stock.pojo.StockMarketIndexInfo;
import com.itheima.stock.pojo.StockRtInfo;
import com.itheima.stock.job.StockTimerTaskService;
import com.itheima.stock.util.DateTimeUtil;
import com.itheima.stock.util.IdWorker;
import com.itheima.stock.util.ParserStockInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Harry
 * @Date 5/29/2022 5:08 PM
 * @Version 1.0
 **/
@Service
@Slf4j
public class StockTimerTaskServiceImpl implements StockTimerTaskService {

    //<editor-fold desc="Mapper bean">
    @Resource
    private ParserStockInfoUtil parserStockInfoUtil;

    @Resource
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Resource
    private StockBusinessMapper stockBusinessMapper;

    @Resource
    private StockRtInfoMapper stockRtInfoMapper;

    @Resource
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;
    //</editor-fold>

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private StockInfoConfig stockInfoConfig;

    @Resource
    private IdWorker idWorker;

    /**
     * 获取国内大盘数据
     */
    @Override
    public void getInnerMarketInfo() {
        // assemble dynamic url
        // http://hq.sinajs.cn/list=s_sh000001,s_sz399001
        String innerUrl = stockInfoConfig.getMarketUrl() + String.join(
                ",",
                stockInfoConfig.getInner());

        // set up request headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");

        // post Requests
        String result = restTemplate.postForObject(innerUrl, new HttpEntity<>(headers), String.class);

        String reg = "(.*)=\"(.+)\";";

        Pattern r = Pattern.compile(reg);

        assert result != null;
        Matcher matcher = r.matcher(result);

        //  collect data from response
        ArrayList<StockMarketIndexInfo> list = new ArrayList<>();
        while (matcher.find()) {
            // get market id
            String marketCode = matcher.group(1);

            // 截取字符串，make it to be like this "s_sh000001"
            String realCode = marketCode.substring(11);
            System.out.println("marketCode = " + realCode);

            // other information
            String other = matcher.group(2);
            String[] others = other.split(",");

            // market name
            String marketName = others[0];
            //当前点
            BigDecimal curPoint = new BigDecimal(others[1]);
            //当前价格
            BigDecimal curPrice = new BigDecimal(others[2]);
            //涨跌率
            BigDecimal upDownRate = new BigDecimal(others[3]);
            //成交量
            Long tradeAmount = Long.valueOf(others[4]);
            //成交金额
            Long tradeVol = Long.valueOf(others[5]);
            //当前日期
            Date now = DateTimeUtil.getDateTimeWithoutSecond(DateTime.now()).toDate();
            //封装对象
            StockMarketIndexInfo info = StockMarketIndexInfo.builder()
                    .markName(marketName)
                    .id(idWorker.nextId() + "")
                    .tradeVolume(tradeVol)
                    .tradeAccount(tradeAmount)
                    .updownRate(upDownRate)
                    .curTime(now)
                    .curPoint(curPoint)
                    .currentPrice(curPrice)
                    .markId(realCode)
                    .build();
            list.add(info);

        }

        log.info("collection's length: {}, content: {}", list.size(), list);

        // batch insert
        if (CollectionUtils.isEmpty(list)) {
            log.warn("");
            return;
        }

        String curTime = DateTime.now().toString(DateTimeFormat.forPattern("yyyyMMddHHmmss"));

        log.info("Collected market info: {}, current Time: {}", list, curTime);

        // TODO: INSERT INTO DATABASE
        int lines = stockMarketIndexInfoMapper.batchInsert(list);

        log.info("{} Lines have been inserted", lines);


    }

    /**
     * 批量获取股票分时数据详情信息
     * http://hq.sinajs.cn/list=sz000002,sh600015
     */
    @Override
    public void getStockRtIndex() {
        // get Stock Id list
        List<String> stockIds = stockBusinessMapper.getStockIds();

        // make ids conform the rules of sina.cn
        stockIds = stockIds.stream().map(id -> id.startsWith("6") ? "sh" + id : "sz" + id)
                .collect(Collectors.toList());

        // make headers
        HttpHeaders headers = new HttpHeaders();

        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // batch processing
        Lists.partition(stockIds, 20).forEach(list -> {

            //拼接股票url地址
            String stockUrl = stockInfoConfig.getMarketUrl() + String.join(",", list);
            //获取响应数据
            String result = restTemplate.postForObject(stockUrl, entity, String.class);

            List<StockRtInfo> infos = parserStockInfoUtil.parser4StockOrMarketInfo(result, 3);

            log.info("数据量：{}", infos.size());

            // batch insert into stock_rt_info
            int lines = stockRtInfoMapper.insertBatch(infos);
            log.info("{} Lines have been inserted into Rt_info table", lines);
        });
    }


    @Override
    public void getStockSectorRtIndex() {
        String result = restTemplate.getForObject(stockInfoConfig.getBlockUrl(), String.class);

        List<StockBlockRtInfo> blockRtInfos = parserStockInfoUtil.parse4StockBlock(result);

        log.info("Block info size: {}", blockRtInfos.size());

        Lists.partition(blockRtInfos, 20).forEach(list -> {

            // batch insert into block_rt_info
            int lines = stockBlockRtInfoMapper.insertBatch(list);

            log.info("Block info inserted {} lines", lines);
        });


    }
}
