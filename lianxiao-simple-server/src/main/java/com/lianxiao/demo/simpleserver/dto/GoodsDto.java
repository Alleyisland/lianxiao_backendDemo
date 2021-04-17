package com.lianxiao.demo.simpleserver.dto;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Column;

public class GoodsDto {
    private long gid;

    private long uid;

    private String gname;

    private int gtype;


    private String gdescription;

    private Double price;

    private String pic_uri;

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public int getGtype() {
        return gtype;
    }

    public void setGtype(int gtype) {
        this.gtype = gtype;
    }

    public String getGdescription() {
        return gdescription;
    }

    public void setGdescription(String gdescription) {
        this.gdescription = gdescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPic_uri() {
        return pic_uri;
    }

    public void setPic_uri(String pic_uri) {
        this.pic_uri = pic_uri;
    }
}
