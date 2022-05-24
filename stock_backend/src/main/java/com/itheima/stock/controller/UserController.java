package com.itheima.stock.controller;

import com.itheima.stock.service.UserService;
import com.itheima.stock.vo.req.LoginReqVo;
import com.itheima.stock.vo.resp.LoginRespVo;
import com.itheima.stock.vo.resp.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description
 * @Author Harry
 * @Date 5/23/2022 6:35 PM
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 生成验证码
     * map结构：
     * code： xxx,
     * rkey: xxx
     *
     * @return
     */
    @GetMapping("/captcha")
    public R<Map> generateCaptcha() {
        return userService.generateCaptcha();
    }


    /**
     * Login
     *
     * @param loginReqVo Encapsulates the data from the request
     * @return object R (result）
     */
    @PostMapping("/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo loginReqVo) {

        System.out.println(loginReqVo);

        return userService.login(loginReqVo);
    }


//    @GetMapping
//    public String test() {
//        return "itcast";
//    }

}
