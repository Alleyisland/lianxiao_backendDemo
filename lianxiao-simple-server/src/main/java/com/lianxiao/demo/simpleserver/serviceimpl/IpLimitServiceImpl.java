package com.lianxiao.demo.simpleserver.serviceimpl;

import com.lianxiao.demo.simpleserver.service.IpLimitService;
import com.lianxiao.demo.simpleserver.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class IpLimitServiceImpl implements IpLimitService {
    private static final String IP_LIMIT = "ip_limit_";
    @Autowired
    private RedisUtils redisUtils;
    private DefaultRedisScript<Long> ipScript;

    @PostConstruct
    private void init(){
        ipScript=new DefaultRedisScript<Long>();
        ipScript.setResultType(Long.class);
        ipScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("ip_limit.lua")));
    }

    public boolean validate(String ip) {
        String key=IP_LIMIT+ip;
        return redisUtils.execIPLimitScript(ipScript,key);
    }
}
