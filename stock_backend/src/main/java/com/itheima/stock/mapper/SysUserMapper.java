package com.itheima.stock.mapper;

import com.itheima.stock.pojo.SysUser;

/**
* @author Harry
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2022-05-23 18:56:05
* @Entity com.itheima.stock.pojo.SysUser
*/
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * Find User info by username
     *
     * @param username String
     * @return user
     */
    SysUser findByUsername(String username);
}
