package com.itheima.stock.mapper;

import com.itheima.stock.common.domain.ForeignMarketDomain;
import com.itheima.stock.pojo.StockMarketIndexInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Harry
 * @description 针对表【stock_market_index_info(股票大盘数据详情表)】的数据库操作Mapper
 * @createDate 2022-05-23 18:56:05
 * @Entity com.itheima.stock.pojo.StockMarketIndexInfo
 */
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);

    
    List<ForeignMarketDomain> selectByIdsAndDate(@Param("ids") List<String> innerIds,
                                                 @Param("lastDate") Date lastDate);


    /**
     * Batch insert inner market info
     *
     * @param list
     * @return
     */
    int innerMarketBatchInsert(List<StockMarketIndexInfo> list);


    /**
     * batch insert foreign market info
     *
     * @param list
     * @return
     */
    int foreignMarketBatchInsert(List<StockMarketIndexInfo> list);


    List<ForeignMarketDomain> getForeignMarketInfo(@Param("ids")List<String> outerIds,
                                                   @Param("lastDate") Date lastDate);
}
