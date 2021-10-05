package com.lianxiao.demo.simpleserver.test;

import com.lianxiao.demo.simpleserver.service.PostService;
import com.lianxiao.demo.simpleserver.serviceimpl.PostServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceTest {

    @Autowired
    private PostService postService;

    @Test
    public void testFindById() {
        System.out.println(postService.findById(1385617011152781312L));
    }

}