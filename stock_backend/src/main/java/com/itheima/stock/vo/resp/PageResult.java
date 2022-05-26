package com.itheima.stock.vo.resp;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author Harry
 * @Date 5/26/2022 12:15 AM
 * @Version 1.0
 **/

/**
 * Utils of Page Divider
 *
 * @param <T> Element type of List 'rows'.
 */
@Data
public class PageResult<T> implements Serializable {

    private Long totalRows;

    private Integer totalPages;

    private Integer pageNum;

    private Integer pageSize;

    private Integer size;

    /**
     * result List
     */
    private List<T> rows;

    public PageResult(PageInfo<T> pageInfo) {
        totalRows = pageInfo.getTotal();
        totalPages = pageInfo.getPages();
        pageNum = pageInfo.getPageNum();
        pageSize = pageInfo.getPageSize();
        size = pageInfo.getSize();
        rows = pageInfo.getList();
    }

}
