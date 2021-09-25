package com.lianxiao.demo.simpleserver;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.LinkedList;

@SpringBootApplication
@MapperScan(basePackages = "com.lianxiao.demo.simpleserver.dao")
@ServletComponentScan(basePackages = "com.lianxiao.demo.simpleserver.filter")
@EnableDubbo
@EnableScheduling
public class LianxiaoSimpleServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(LianxiaoSimpleServerApplication.class, args);
    }

//    @NacosInjected
//    private NamingService namingService;
//
//    @PostConstruct
//    public void registerService() throws NacosException{
//        namingService.registerInstance("test","127.0.0.1",5555);
//    }
}
