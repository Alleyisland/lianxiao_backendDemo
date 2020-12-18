package com.lianxiao.demo.simpleserver.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lianxiao.demo.simpleserver.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "`Goods`")
public class Goods extends BaseEntity {
    /**
     * gid
     */
    @Column(name = "`gid`")
    @JSONField(ordinal = 1)
    private long gid;

    /**
     * gtype
     */
    @Column(name = "`gtype`")
    @JSONField(ordinal = 2)
    private int gtype;

    /**
     * uid
     */
    @Column(name = "`uid`")
    @JSONField(ordinal = 3)
    private long uid;

    /**
     * 商品名称 gname
     */
    @Column(name = "`gname`")
    @JSONField(ordinal = 4)
    private String gname;

    /**
     * 商品描述 gdescription
     */
    @Column(name = "`gdescription`")
    @JSONField(ordinal = 5)
    private String gdescription;

    /**
     * 商品价格 price
     */
    @Column(name = "`price`")
    @JSONField(ordinal = 6)
    private Double price;

    /**
     * 商品图片 pic_uri
     */
    @Column(name = "`pic_uri`")
    @JSONField(ordinal = 7)
    private String pic_uri;

    public Goods(long gid, int gtype, long uid, String gname, String gdescription, double price, String pic_uri) {
        this.gid = gid;
        this.gtype = gtype;
        this.uid = uid;
        this.gname = gname;
        this.gdescription = gdescription;
        this.price = price;
        this.pic_uri = pic_uri;
    }

    public Goods() {

    }

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public int getGtype() {
        return gtype;
    }

    public void setGtype(int gtype) {
        this.gtype = gtype;
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

    public String getGdescription() {
        return gdescription;
    }

    public void setGdescription(String gdescription) {
        this.gdescription = gdescription;
    }

    public double getPrice() {
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
