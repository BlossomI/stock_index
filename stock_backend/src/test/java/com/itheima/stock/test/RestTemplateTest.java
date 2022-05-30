package com.itheima.stock.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author Harry
 * @Date 5/29/2022 4:41 PM
 * @Version 1.0
 **/
@SpringBootTest
public class RestTemplateTest {


    @Test
    public void test1(){

        String line = "This order was placed for QT3000! Yes?";
        String pattern = "(\\D*)(\\d+)(.*)";

        // create Pattern object
        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(line);

        if (m.find()) {
            System.out.println("m.group(0) = " + m.group(0));
            System.out.println("m.group(0) = " + m.group(1));
            System.out.println("m.group(0) = " + m.group(2));
            System.out.println("m.group(0) = " + m.group(3));
        }else {
            System.out.println("NO MATCH");
        }

    }

}
