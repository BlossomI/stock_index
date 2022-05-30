package com.itheima.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Description
 * @Author Harry
 * @Date 5/29/2022 6:54 PM
 * @Version 1.0
 **/
@Configuration
public class HttpClientConfig {

    /**
     * define RestTemplate Bean
     *
     * @return restTemplate object
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
