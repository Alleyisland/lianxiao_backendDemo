package com.lianxiao.demo.simpleserver.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RedissonConfig {
    @Value("${spring.redis.host}")
    private String redisHostName;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;

    @Bean(destroyMethod="shutdown")
    RedissonClient redisson() throws IOException {
        //1、创建配置
        Config config = new Config();
        String redisAddr="redis://"+redisHostName+":"+port;
        config.useSingleServer()
                .setAddress(redisAddr)
                .setPassword(password);
        return Redisson.create(config);
    }

}