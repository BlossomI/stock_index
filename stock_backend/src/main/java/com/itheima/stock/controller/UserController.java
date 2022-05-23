package com.itheima.stock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description
 * @Author Harry
 * @Date 5/23/2022 6:35 PM
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping
    public String test() {
        return "itcast";
    }

}
