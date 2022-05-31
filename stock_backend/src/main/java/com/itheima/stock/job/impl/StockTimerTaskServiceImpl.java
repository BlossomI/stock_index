package com.itheima.stock.job.impl;

import com.google.common.collect.Lists;
import com.itheima.stock.common.domain.StockInfoConfig;
import com.itheima.stock.job.StockTimerTaskService;
import com.itheima.stock.mapper.StockBlockRtInfoMapper;
import com.itheima.stock.mapper.StockBusinessMapper;
import com.itheima.stock.mapper.StockMarketIndexInfoMapper;
import com.itheima.stock.mapper.StockRtInfoMapper;
import com.itheima.stock.pojo.StockBlockRtInfo;
import com.itheima.stock.pojo.StockRtInfo;
import com.itheima.stock.util.IdWorker;
import com.itheima.stock.util.ParserStockInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
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
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Resource
    private StockBusinessMapper stockBusinessMapper;

    @Resource
    private StockRtInfoMapper stockRtInfoMapper;

    @Resource
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;
    //</editor-fold>
    @Resource
    private ParserStockInfoUtil parserStockInfoUtil;

    /**
     * 注入线程池对象
     */
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * restTemplate 获取实时数据对象
     */
    @Resource
    private RestTemplate restTemplate;

    /**
     * 获取yml配置信息对象
     */
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


        //-------------------------- post Requests ---------------------------------
        String result = restTemplate.postForObject(innerUrl, new HttpEntity<>(headers), String.class);

        // parse full result into info list
        List list = parserStockInfoUtil.parser4StockOrMarketInfo(result, 1);

        log.info("collection's length: {}, content: {}", list.size(), list);


        // ------------------------ batch insert ------------------------------------
        if (CollectionUtils.isEmpty(list)) {
            log.warn("");
            return;
        }

        String curTime = DateTime.now().toString(DateTimeFormat.forPattern("yyyyMMddHHmmss"));
        System.out.println(curTime);
        log.info("Collected market info: {}, current Time: {}", list, curTime);
        int lines = stockMarketIndexInfoMapper.innerMarketBatchInsert(list);
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

            // each shard of data starts a Thread to execute tasks
            threadPoolTaskExecutor.execute(() -> {

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
        });
    }

    /**
     * 获取板块数据, 多线程分片插入数据库
     */
    @Override
    public void getStockSectorRtIndex() {
        String result = restTemplate.getForObject(stockInfoConfig.getBlockUrl(), String.class);

        List<StockBlockRtInfo> blockRtInfos = parserStockInfoUtil.parse4StockBlock(result);

        log.info("Block info size: {}", blockRtInfos.size());

        Lists.partition(blockRtInfos, 20).forEach(list -> {

            // each shard of data starts a Thread to execute tasks
            threadPoolTaskExecutor.execute(() -> {

                // batch insert into block_rt_info
                int lines = stockBlockRtInfoMapper.insertBatch(list);

                log.info("Block info inserted {} lines", lines);

            });

        });


    }

    @Override
    public void getForeignMarketInfo() {
        // assemble dynamic url
        String foreignUrl = stockInfoConfig.getForeignUrl() + String.join(",",
                stockInfoConfig.getOuter());

        // set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");

        // -------------------------- post requests ---------------------------------
        String result = restTemplate.postForObject(foreignUrl, new HttpEntity<>(headers), String.class);
        System.out.println(result);

        List list = parserStockInfoUtil.parser4StockOrMarketInfo(result, 2);

        log.info("collection's length: {}, content: {}", list.size(), list);

        // -------------------------- batch insert --------------------------------
        if (CollectionUtils.isEmpty(list)) {
            log.warn("");
            return;
        }

        String curTime = DateTime.now().toString(DateTimeFormat.forPattern("yyyyMMddHHmmss"));
        log.info("Collected market info: {}, current Time: {}", list, curTime);
        int lines = stockMarketIndexInfoMapper.foreignMarketBatchInsert(list);
        log.info("{} Lines have been inserted", lines);


    }
}
