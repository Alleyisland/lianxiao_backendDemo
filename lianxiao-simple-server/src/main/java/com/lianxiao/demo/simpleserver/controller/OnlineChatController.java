package com.lianxiao.demo.simpleserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.lianxiao.demo.simpleserver.exception.AppException;
import com.lianxiao.demo.simpleserver.model.ChatMessage;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/chat/private/{id}")
public class OnlineChatController {
    private Session session;
    private long id;
    private static ConcurrentHashMap<Long, OnlineChatController> websockets = new ConcurrentHashMap<>();

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    public static OnlineChatController onlineChatController;

    @PostConstruct
    public void init() {
        onlineChatController = this;
        onlineChatController.rocketMQTemplate = this.rocketMQTemplate;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "id") long id) {
        this.session = session;
        this.id = id;
        websockets.put(id, this);
    }

    @OnClose
    public void onClose() {
        websockets.remove(id);

    }

    @OnMessage
    public void OnMessage(String json) {
        ChatMessage message = JSONObject.parseObject(json, ChatMessage.class);

        privateSend(message);
    }

    public void privateSend(ChatMessage message) {
        //消息接收者在线,直接转发
        if (websockets.containsKey(message.getReceiverId())) {
            try {
                websockets.get(message.getReceiverId()).session.getBasicRemote().sendText(JSONObject.toJSONString(message));
            } catch (IOException e) {
                e.printStackTrace();
                throw new AppException("-1","目标在线，但消息发送失败");
            }
        }
        //消息接收者离线,写入mq
        else {
            long receiverId = message.getReceiverId();
            long senderId = message.getSenderId();
            String msg = message.getContent();
            String topic_name = "chat_topic";
            String mqTag = "private_msg_to_" + receiverId;
            String mqKey = "private_msg_to_" + receiverId;
            HashMap<String, Object> payload = new HashMap<>();
            payload.put("id", new Random().nextInt(100));
            payload.put("type", 1);
            payload.put("datetime", new Date().getTime());
            payload.put("senderId", senderId);
            payload.put("receiverId", receiverId);
            payload.put("content", msg);
            Message offLineMessage = MessageBuilder.withPayload(payload).setHeader("KEYS", mqKey).build();
            SendResult result = realSend(topic_name, mqTag, offLineMessage, receiverId);
        }
    }

    public SendResult realSend(String topic_name, String mqTag, Message message, long receiverId) {
        onlineChatController.rocketMQTemplate.setMessageQueueSelector(new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, org.apache.rocketmq.common.message.Message message, Object o) {
                int qIndex = Math.abs(o.hashCode() % 4);
                return mqs.get(qIndex);
            }
        });
        return onlineChatController.rocketMQTemplate.syncSendOrderly(topic_name + ":" + mqTag, message, String.valueOf(receiverId));
    }
}

