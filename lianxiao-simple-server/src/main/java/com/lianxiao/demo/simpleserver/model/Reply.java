package com.lianxiao.demo.simpleserver.model;

import com.lianxiao.demo.simpleserver.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "`reply`")
public class Reply extends BaseEntity {

    @Column(name = "`rid`")
    private long rid;

    @Column(name = "`pid`")
    private long pid;

    @Column(name = "`uid`")
    private long uid;

    @Column(name = "`content`")
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


}
