package com.lianxiao.demo.simpleserver.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class ReplyDto {

    @JSONField(ordinal = 1)
    private long rid;

    @JSONField(ordinal = 2)
    private long pid;

    @JSONField(ordinal = 3)
    private long uid;

    @JSONField(ordinal = 4)
    private String content;

    public ReplyDto(long rid, long pid, long uid, String content) {
        this.rid = rid;
        this.pid = pid;
        this.uid = uid;
        this.content = content;
    }

    public ReplyDto() {
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
