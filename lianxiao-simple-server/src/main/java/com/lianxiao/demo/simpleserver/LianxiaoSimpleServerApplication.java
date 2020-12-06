package com.lianxiao.demo.simpleserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lianxiao.demo.simpleserver.dao")
public class LianxiaoSimpleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LianxiaoSimpleServerApplication.class, args);
    }
}
