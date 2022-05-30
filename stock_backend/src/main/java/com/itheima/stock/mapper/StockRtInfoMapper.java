package com.itheima.stock.mapper;

import com.itheima.stock.common.domain.Stock4MinDomain;
import com.itheima.stock.common.domain.StockDailyDKLineDomain;
import com.itheima.stock.common.domain.StockUpDownDomain;
import com.itheima.stock.pojo.StockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    List<Map> upDownCount(@Param("avlDate") Date curDate,
                          @Param("openDate") Date openDate,
                          @Param("flag") Integer flag);

    List<Map> stockTradeVolCount(@Param("marketIds") List<String> inner,
                                 @Param("openTime") Date tOpenTime,
                                 @Param("nowTime") Date tDateTime);

    List<Map> stockUpDownScopeCount(@Param("avlDate") Date avlDate);


    /**
     * 功能描述：查询单个个股的分时行情数据，也就是统计指定股票T日每分钟的交易数据；
     * 如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询时间点
     *
     * @param code 股票编码
     */
    List<Stock4MinDomain> stockScreenTimeSharing(@Param("stockCode") String code,
                                                 @Param("startDate") Date openDate,
                                                 @Param("endDate") Date curDate);

    List<StockDailyDKLineDomain> stockScreenDkLine(@Param("stockCode") String stockCode,
                                                   @Param("pre20Dat") Date pre20Day,
                                                   @Param("curDate") Date curTime);

    /**
     * 批量插入功能
     * @param stockRtInfoList
     */
    int insertBatch(List<StockRtInfo> stockRtInfoList);

}
