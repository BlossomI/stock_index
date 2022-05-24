package com.itheima.stock.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @Description
 * @Author Harry
 * @Date 5/24/2022 7:08 PM
 * @Version 1.0
 **/
@SpringBootTest
public class SpringDataRedisTest {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test01(){
        redisTemplate.opsForValue().set("name","zhangsan");

        Object name = redisTemplate.opsForValue().get("name");

        System.out.println("name = " + name);
    }
}
