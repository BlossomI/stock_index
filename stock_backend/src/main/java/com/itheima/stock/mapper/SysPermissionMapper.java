package com.itheima.stock.mapper;

import com.itheima.stock.pojo.SysPermission;

/**
* @author Harry
* @description 针对表【sys_permission(权限表（菜单）)】的数据库操作Mapper
* @createDate 2022-05-23 18:56:05
* @Entity com.itheima.stock.pojo.SysPermission
*/
public interface SysPermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

}
