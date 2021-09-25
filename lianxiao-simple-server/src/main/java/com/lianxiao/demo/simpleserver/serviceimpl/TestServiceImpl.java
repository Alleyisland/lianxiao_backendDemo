package com.lianxiao.demo.simpleserver.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lianxiao.demo.simpleserver.service.TestService;
import org.springframework.stereotype.Component;

@Service(group = "test-nacos", retries = 0, timeout = 10000)
@Component
public class TestServiceImpl implements TestService {

}
