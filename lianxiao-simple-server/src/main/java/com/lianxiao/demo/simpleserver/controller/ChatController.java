package com.lianxiao.demo.simpleserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lianxiao.demo.simpleserver.model.ChatMessage;
import com.lianxiao.demo.simpleserver.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.PullStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;

@RestController
@RequestMapping("/chat")
@Api(description = "聊天接口")
public class ChatController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RedisUtils redisUtils;


    private static final Map<MessageQueue, Long> offsetTable = new HashMap<>();
    DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("consumer_group");

    public static final String CHAT_TOPIC="chat_topic";
    public static final String GROUP_CHAT_TOPIC="group_chat_topic";

    public static final String GROUP_CHAT_KEY_PREFIX="group_chat_";

    public static final String PRIVATE_MSG_PREFIX="private_msg_to_";
    public static final String GROUP_CHAT_MSG_PREFIX="group_chat_msg_to_";

    @PostConstruct
    public void init() throws MQClientException {
        consumer.start();
    }

    /**
     * 普通消息投递
     */
    @PostMapping("/send")
    @ResponseBody
    @ApiOperation(value = "发消息", notes = "发消息")
    public String send(@ApiParam(name = "senderId", value = "发送者id",required = true) @RequestParam long senderId,
                       @ApiParam(name = "receiverId", value = "接收者id",required = true) @RequestParam long receiverId,
                       @ApiParam(name = "msg", value = "消息内容",required = true) @RequestParam String msg) {
        String mqTag = PRIVATE_MSG_PREFIX + receiverId;
        String mqKey = PRIVATE_MSG_PREFIX + receiverId;
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("id", new Random().nextInt(100));
        payload.put("type", 1);
        payload.put("datetime", new Date().getTime());
        payload.put("senderId", senderId);
        payload.put("receiverId", receiverId);
        payload.put("content", msg);
        Message message = MessageBuilder.withPayload(payload).setHeader("KEYS", mqKey).build();
        System.out.println(message.toString());
        SendResult result = realSend(CHAT_TOPIC, mqTag, message, receiverId);
        return "投递消息 => " + msg + " => 成功";
    }

    public SendResult realSend(String topic_name, String mqTag, Message message, long receiverId) {
        rocketMQTemplate.setMessageQueueSelector(new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, org.apache.rocketmq.common.message.Message message, Object o) {
                int qIndex = Math.abs(o.hashCode() % 4);
                return mqs.get(qIndex);
            }
        });
        return rocketMQTemplate.syncSendOrderly(topic_name + ":" + mqTag, message, String.valueOf(receiverId));
    }

    /**
     * 消息批量更新
     */
    @GetMapping("/update")
    @ResponseBody
    @ApiOperation(value = "收取离线消息", notes = "收取离线消息")
    public String update(@ApiParam(name = "receiverId", value = "接收者id",required = true) @RequestParam long receiverId) throws Exception {
        List<ChatMessage> msgs = new ArrayList<>();
        String topic_name = CHAT_TOPIC;
        String mqTag = PRIVATE_MSG_PREFIX + receiverId;
        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues(topic_name);
        consumer.setConsumerPullTimeoutMillis(100L);
        consumer.setBrokerSuspendMaxTimeMillis(100L);
        for (MessageQueue mq : mqs) {
            if (mq.getQueueId() == String.valueOf(receiverId).hashCode() % 4) {
                PullResult pullResult = consumer.pullBlockIfNotFound(mq, mqTag, getMessageQueueOffset(mq), 32);
                if (pullResult.getPullStatus() == PullStatus.FOUND) {
                    List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                    for (MessageExt m : messageExtList) {
                        ChatMessage message = JSONObject.parseObject(new String(m.getBody()), ChatMessage.class);
                        msgs.add(message);
                    }
                }
            }
        }
        return JSON.toJSONString(msgs);
    }

    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        offsetTable.put(mq, offset);
    }

    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = offsetTable.get(mq);
        if (offset != null)
            return offset;
        return 0;
    }

    /**
     * 群聊消息投递
     */
    @PostMapping("/group_send")
    @ResponseBody
    @ApiOperation(value = "群聊发消息", notes = "群聊发消息")
    public String groupSend(@ApiParam(name = "senderId", value = "发送者id",required = true) @RequestParam long senderId,
                       @ApiParam(name = "groupId", value = "群组id",required = true) @RequestParam long groupId,
                       @ApiParam(name = "msg", value = "消息内容",required = true) @RequestParam String msg) {
        String topic_name = GROUP_CHAT_TOPIC;
        String mqKey = GROUP_CHAT_KEY_PREFIX + groupId;
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("id", new Random().nextInt(100));
        payload.put("type", 2);
        payload.put("datetime", new Date().getTime());
        payload.put("senderId", senderId);
        payload.put("groupId", groupId);
        payload.put("content", msg);
        Message message = MessageBuilder.withPayload(payload).setHeader("KEYS", mqKey).build();
        System.out.println(message.toString());
        SendResult result = realGroupSend(topic_name, GROUP_CHAT_MSG_PREFIX, message, groupId);
        return "投递消息 => " + msg + " => 成功";
    }

    private SendResult realGroupSend(String topic_name, String mqTagPrefix, Message message, long groupId) {
        String key=GROUP_CHAT_KEY_PREFIX+groupId;
        List<Long> memberIds=redisUtils.getList(key);
        rocketMQTemplate.setMessageQueueSelector(new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, org.apache.rocketmq.common.message.Message message, Object o) {
                int qIndex = Math.abs(o.hashCode() % 4);
                return mqs.get(qIndex);
            }
        });
        SendResult sendResult=null;
        for(long memberId:memberIds) {
            String mqTag = mqTagPrefix + memberId;
            sendResult=rocketMQTemplate.syncSendOrderly(topic_name + ":" + mqTag, message, String.valueOf(memberId));
            if(!sendResult.getSendStatus().equals(SendStatus.SEND_OK))
                return sendResult;
        }
        return sendResult;
    }

    /**
     * 群聊消息批量更新
     */
    @GetMapping("/group_update")
    @ResponseBody
    @ApiOperation(value = "收取离线群聊消息", notes = "收取离线群聊消息")
    public String groupUpdate(@ApiParam(name = "receiverId", value = "接收者id",required = true) @RequestParam long receiverId) throws Exception {
        List<ChatMessage> msgs = new ArrayList<>();
        String mqTag = GROUP_CHAT_MSG_PREFIX + receiverId;
        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues(GROUP_CHAT_TOPIC);
        consumer.setConsumerPullTimeoutMillis(100L);
        consumer.setBrokerSuspendMaxTimeMillis(100L);
        for (MessageQueue mq : mqs) {
            if (mq.getQueueId() == String.valueOf(receiverId).hashCode() % 4) {
                PullResult pullResult = consumer.pullBlockIfNotFound(mq, mqTag, getMessageQueueOffset(mq), 32);
                if (pullResult.getPullStatus() == PullStatus.FOUND) {
                    List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                    for (MessageExt m : messageExtList) {
                        ChatMessage message = JSONObject.parseObject(new String(m.getBody()), ChatMessage.class);
                        msgs.add(message);
                    }
                }
            }
        }
        return JSON.toJSONString(msgs);
    }

}
