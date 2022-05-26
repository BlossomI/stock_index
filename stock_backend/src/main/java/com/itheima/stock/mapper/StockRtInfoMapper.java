package com.itheima.stock.mapper;

import com.itheima.stock.common.domain.StockUpDownDomain;
import com.itheima.stock.pojo.StockRtInfo;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
* @author Harry
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2022-05-23 18:56:05
* @Entity com.itheima.stock.pojo.StockRtInfo
*/
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

    List<StockUpDownDomain> stockIncreaseLimit(Date curDateTime);

    List<StockUpDownDomain> stockAll();
}
