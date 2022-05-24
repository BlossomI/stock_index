package com.itheima.stock.test;

import com.itheima.stock.util.IdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * @Description
 * @Author Harry
 * @Date 5/24/2022 7:25 PM
 * @Version 1.0
 **/
@SpringBootTest
public class IdworkerTest {
    @Autowired
    private IdWorker idWorker;

    @Test
    public void test01() {
        for (int i = 0; i < 100; i++) {
            System.out.println(idWorker.nextId());
        }
    }

    @Test
    public void test02() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(UUID.randomUUID().toString());
        }
    }
}
