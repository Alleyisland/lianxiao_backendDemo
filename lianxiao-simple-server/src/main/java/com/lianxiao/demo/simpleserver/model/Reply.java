package com.lianxiao.demo.simpleserver.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lianxiao.demo.simpleserver.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "`reply`")
public class Reply extends BaseEntity {

    @Column(name = "`rid`")
    @JSONField(ordinal = 1)
    private long rid;

    @Column(name = "`pid`")
    @JSONField(ordinal = 2)
    private long pid;

    @Column(name = "`uid`")
    @JSONField(ordinal = 3)
    private long uid;

    @Column(name = "`content`")
    @JSONField(ordinal = 4)
    private String content;

    public Reply(long rid, long pid, long uid, String content) {
        this.rid = rid;
        this.pid = pid;
        this.uid = uid;
        this.content = content;
    }

    public Reply() {
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

    @Override
    public String toString() {
        return "Reply{" +
                "rid=" + rid +
                ", pid=" + pid +
                ", uid=" + uid +
                ", content='" + content + '\'' +
                '}';
    }
}
