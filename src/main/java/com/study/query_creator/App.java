package com.study.query_creator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan("com.study.query_creator.dao")
@ImportResource("classpath:spring-context.xml")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
