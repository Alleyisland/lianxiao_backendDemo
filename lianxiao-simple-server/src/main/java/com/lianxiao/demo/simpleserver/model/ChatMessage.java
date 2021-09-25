package com.lianxiao.demo.simpleserver.model;

import com.alibaba.fastjson.annotation.JSONField;

public class ChatMessage {

    @JSONField(ordinal = 1)
    long id;

    @JSONField(ordinal = 2)
    int type;

    @JSONField(ordinal = 3)
    long senderId;

    @JSONField(ordinal = 4)
    long receiverId;

    @JSONField(ordinal = 5)
    long groupId;

    @JSONField(ordinal = 6)
    long datetime;

    @JSONField(ordinal = 7)
    String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", type=" + type +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", groupId=" + groupId +
                ", datetime=" + datetime +
                ", content='" + content + '\'' +
                '}';
    }
}
