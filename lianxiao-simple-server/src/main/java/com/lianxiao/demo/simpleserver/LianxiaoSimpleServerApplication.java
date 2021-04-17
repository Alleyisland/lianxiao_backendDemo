package com.lianxiao.demo.simpleserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.lianxiao.demo.simpleserver.dao")
@ServletComponentScan(basePackages = "com.lianxiao.demo.simpleserver.filter")
public class LianxiaoSimpleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LianxiaoSimpleServerApplication.class, args);
    }
}
