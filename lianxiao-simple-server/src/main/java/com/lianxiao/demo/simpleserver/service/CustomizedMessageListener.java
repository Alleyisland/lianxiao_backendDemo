package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.model.ChatMessage;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
// topic 主题要与消息生产者一致
@RocketMQMessageListener(consumerGroup = "consumer-group", topic = "chat_topic",
        selectorExpression = "private_msg")
public class CustomizedMessageListener implements RocketMQListener<ChatMessage> {
    @Override
    public void onMessage(ChatMessage message) {
        anyAction(message);
    }

    //任意处理单条消息的逻辑
    public void anyAction(ChatMessage message) {
        System.out.println(("消费到消息 => " + message.toString()));
    }
}