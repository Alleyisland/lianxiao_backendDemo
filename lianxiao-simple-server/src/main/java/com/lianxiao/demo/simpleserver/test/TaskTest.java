package com.lianxiao.demo.simpleserver.test;


import com.lianxiao.demo.simpleserver.task.ScheduledTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskTest {

    @Autowired
    private ScheduledTask task;

    @Test
    public void testCrawlWeiBo() throws IOException, InterruptedException {
        task.crawlWeiBo();
    }
}
