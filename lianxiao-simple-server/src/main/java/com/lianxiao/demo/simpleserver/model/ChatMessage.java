package com.lianxiao.demo.simpleserver.model;

public class ChatMessage {
    long id;
    int type;
    long senderId;
    long receiverId;
    long datetime;
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

    @Override
    public String toString() {
        return "ChatMessage{" +
                "msg_id=" + id +
                ", type=" + type +
                ", date_time=" + datetime +
                ", sender_id=" + senderId +
                ", receiver_id=" + receiverId +
                ", content='" + content + '\'' +
                '}';
    }
}
