package com.itheima.stock.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.google.common.base.Strings;
import com.itheima.stock.common.enums.ResponseCode;
import com.itheima.stock.mapper.SysUserMapper;
import com.itheima.stock.pojo.SysUser;
import com.itheima.stock.service.UserService;
import com.itheima.stock.util.IdWorker;
import com.itheima.stock.vo.req.LoginReqVo;
import com.itheima.stock.vo.resp.LoginRespVo;
import com.itheima.stock.vo.resp.R;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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


    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @Resource
    private IdWorker idWorker;


    @Override
    public R<Map> generateCaptcha() {

        // 1. 生成四位数验证码
        String checkCode = RandomStringUtils.randomNumeric(4);

        // 2. 获取唯一id
        String rKey = String.valueOf(idWorker.nextId());

        // 3. 将验证码存入redis中，并设置有效期一分钟
        redisTemplate.opsForValue().set(rKey, checkCode, 60, TimeUnit.SECONDS);

        // 4. 组装数据
        HashMap<String, String> map = new HashMap<>();
        map.put("rkey", rKey);
        map.put("code", checkCode);

        System.out.println("map = " + map);

        return R.ok(map);
    }

    @Override
    public R<LoginRespVo> login(LoginReqVo loginReqVo) {
        // if param is null or empty, return R with String error
        if (loginReqVo == null
                || Strings.isNullOrEmpty(loginReqVo.getUsername())
                || Strings.isNullOrEmpty(loginReqVo.getPassword())
                || Strings.isNullOrEmpty(loginReqVo.getCode())
                || Strings.isNullOrEmpty(loginReqVo.getRkey())
        ) {
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        System.out.println("data exists, start query and conpare password");



        // =======================验证码校验=================================
        String rCode = redisTemplate.opsForValue().get(loginReqVo.getRkey());
        // 如果不匹配则返回验证码错误
        if (Strings.isNullOrEmpty(rCode) || !rCode.equals(loginReqVo.getCode())) {
            return R.error(ResponseCode.SYSTEM_VERIFY_CODE_ERROR.getMessage());
        }

        // redis 清除key
        redisTemplate.delete(loginReqVo.getRkey());




        // ========================比对密码===========================================
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
