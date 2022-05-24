package com.itheima.stock.service;

import com.itheima.stock.vo.req.LoginReqVo;
import com.itheima.stock.vo.resp.LoginRespVo;
import com.itheima.stock.vo.resp.R;

import java.util.Map;

/**
 * @Description
 * @Author Harry
 * @Date 5/23/2022 10:20 PM
 * @Version 1.0
 **/
public interface UserService {

    R<LoginRespVo> login(LoginReqVo loginReqVo);

    R<Map> generateCaptcha();
}


