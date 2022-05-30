package com.itheima.stock.mapper;

import com.itheima.stock.pojo.StockBusiness;

import java.util.List;

/**
* @author Harry
* @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
* @createDate 2022-05-23 18:56:05
* @Entity com.itheima.stock.pojo.StockBusiness
*/
public interface StockBusinessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBusiness record);

    int insertSelective(StockBusiness record);

    StockBusiness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBusiness record);

    int updateByPrimaryKey(StockBusiness record);

    List<StockBusiness> getAll();

    /**
     * 获取所有股票的code
     * @return code List
     */
    List<String> getStockIds();

}
