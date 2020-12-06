package com.lianxiao.demo.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class LianxiaoEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LianxiaoEurekaApplication.class, args);
    }

}
