package com.itheima.stock.service;

import com.itheima.stock.common.domain.InnerMarketDomain;
import com.itheima.stock.common.domain.StockBlockDomain;
import com.itheima.stock.common.domain.StockUpDownDomain;
import com.itheima.stock.pojo.StockBlockRtInfo;
import com.itheima.stock.pojo.StockBusiness;
import com.itheima.stock.vo.resp.PageResult;
import com.itheima.stock.vo.resp.R;

import java.util.List;

/**
 * @Description
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
}
