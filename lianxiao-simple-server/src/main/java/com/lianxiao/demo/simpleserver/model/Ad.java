package com.lianxiao.demo.simpleserver.model;

import com.lianxiao.demo.simpleserver.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "`ad`")
public class Ad extends BaseEntity {
    @Column(name = "`aid`")
    private int aid;

    @Column(name = "`gid`")
    private int gid;

    @Column(name = "`word`")
    private String word;

    public Ad(int aid, int gid, String word) {
        this.aid = aid;
        this.gid = gid;
        this.word = word;
    }

    public Ad() {

    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
