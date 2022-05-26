package com.itheima.stock.service;

import com.itheima.stock.common.domain.InnerMarketDomain;
import com.itheima.stock.common.domain.StockBlockDomain;
import com.itheima.stock.common.domain.StockUpDownDomain;
import com.itheima.stock.pojo.StockBlockRtInfo;
import com.itheima.stock.pojo.StockBusiness;
import com.itheima.stock.vo.resp.PageResult;
import com.itheima.stock.vo.resp.R;

import java.util.List;
import java.util.Map;

/**
 * @Description stock业务层接口
 * @Author Harry
 * @Date 5/23/2022 7:59 PM
 * @Version 1.0
 **/

public interface StockService {

//    List<StockBusiness> getAllBusiness();

    R<List<InnerMarketDomain>> InnerIndexAll();

    R<List<StockBlockDomain>> sectorAllLimit();

    R<List<StockUpDownDomain>> stockIncreaseLimit();

    R<PageResult<StockUpDownDomain>> stockPage(Integer page, Integer pageSize);

     /**
     *   Description：沪深两市涨跌停分时行情数据查询，查询T日每分钟的涨跌停数据（T：当前股票交易日）
     * 		          查询每分钟的涨停和跌停的数据的同级；
     * 		          如果不在股票的交易日内，那么就统计最近的股票交易下的数据
     * 	 map:
     * 	    upList:涨停数据统计
     * 	    downList:跌停数据统计
     * @return
     */
    R<Map> upDownCount();
}
