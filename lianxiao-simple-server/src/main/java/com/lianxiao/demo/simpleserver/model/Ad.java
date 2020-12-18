package com.lianxiao.demo.simpleserver.model;

import com.lianxiao.demo.simpleserver.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "`ad`")
public class Ad extends BaseEntity {
    @Column(name = "`aid`")
    private long aid;

    @Column(name = "`gid`")
    private long gid;

    @Column(name = "`word`")
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
}
