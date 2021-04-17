package com.lianxiao.demo.simpleserver.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lianxiao.demo.simpleserver.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "`ad`")
public class Ad extends BaseEntity {
    @Column(name = "`aid`")
    @JSONField(ordinal = 1)
    private long aid;

    @Column(name = "`gid`")
    @JSONField(ordinal = 2)
    private long gid;

    @Column(name = "`word`")
    @JSONField(ordinal = 3)
    private String word;

    public Ad(long aid, long gid, String word) {
        this.aid = aid;
        this.gid = gid;
        this.word = word;
    }

    public Ad() {

    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "aid=" + aid +
                ", gid=" + gid +
                ", word='" + word + '\'' +
                '}';
    }
}
