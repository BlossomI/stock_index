package com.itheima.stock.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.google.common.base.Strings;
import com.itheima.stock.common.enums.ResponseCode;
import com.itheima.stock.mapper.SysUserMapper;
import com.itheima.stock.pojo.SysUser;
import com.itheima.stock.service.UserService;
import com.itheima.stock.vo.req.LoginReqVo;
import com.itheima.stock.vo.resp.LoginRespVo;
import com.itheima.stock.vo.resp.R;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author Harry
 * @Date 5/23/2022 10:21 PM
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {


    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public R<LoginRespVo> login(LoginReqVo loginReqVo) {

        // if param is null or empty, return R with String error
        if (loginReqVo == null || Strings.isNullOrEmpty(loginReqVo.getUsername()) ||
                Strings.isNullOrEmpty(loginReqVo.getPassword())) {
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        System.out.println("data exists, start query and conpare password");

        // if not null, then query data and compare
        SysUser user = sysUserMapper.findByUsername(loginReqVo.getUsername());

        // if user is null or password don't match, return password error
        if (user == null || !passwordEncoder.matches(loginReqVo.getPassword(), user.getPassword())) {
            return R.error(ResponseCode.SYSTEM_PASSWORD_ERROR.getMessage());
        }

        System.out.println("Validation passed!");

        // then, assemble login success data
        LoginRespVo loginRespVo = new LoginRespVo();

        //属性名称与类型必须相同，否则copy不到
        BeanUtils.copyProperties(user, loginRespVo);

        return R.ok(loginRespVo);
    }
}
