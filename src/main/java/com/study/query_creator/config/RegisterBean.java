package com.study.query_creator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RegisterBean {
    @Bean
    public RestTemplate build() {
        return new RestTemplate();
    }
}
