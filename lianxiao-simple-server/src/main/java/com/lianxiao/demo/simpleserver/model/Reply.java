package com.lianxiao.demo.simpleserver.model;

import com.lianxiao.demo.simpleserver.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "`reply`")
public class Reply extends BaseEntity {

    @Column(name = "`rid`")
    private Integer rid;

    @Column(name = "`uid`")
    private Integer uid;

    @Column(name = "`content`")
    private String content;

    public Reply(Integer rid, Integer uid, String content) {
        this.rid = rid;
        this.uid = uid;
        this.content = content;
    }

    public Reply() {
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
