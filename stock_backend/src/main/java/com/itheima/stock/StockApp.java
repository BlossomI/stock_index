package com.itheima.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @Description
 * @Author Harry
 * @Date 5/23/2022 6:29 PM
 * @Version 1.0
 **/
@SpringBootApplication
@MapperScan("com.itheima.dao")
public class StockApp {
    public static void main(String[] args) {
        SpringApplication.run(StockApp.class, args);
    }
}
