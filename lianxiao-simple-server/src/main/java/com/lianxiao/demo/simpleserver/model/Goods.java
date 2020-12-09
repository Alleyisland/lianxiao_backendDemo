package com.lianxiao.demo.simpleserver.model;
import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;
import com.lianxiao.demo.simpleserver.base.BaseEntity;

@Table(name="`Goods`")
public class Goods extends BaseEntity {
    /**
     * gid
     */
    @Column(name = "`gid`")
    @JSONField(ordinal = 1)
    private String gid;

    /**
     * gtype
     */
    @Column(name = "`gtype`")
    @JSONField(ordinal = 2)
    private String gtype;

    /**
     * uid
     */
    @Column(name = "`uid`")
    @JSONField(ordinal = 3)
    private String uid;

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

    public Goods(String gid, String gtype, String uid, String gname, String gdescription, Double price, String pic_uri) {
        this.gid = gid;
        this.gtype = gtype;
        this.uid = uid;
        this.gname = gname;
        this.gdescription = gdescription;
        this.price = price;
        this.pic_uri = pic_uri;
    }

    public Goods(){

    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getGtype() {
        return gtype;
    }

    public void setGtype(String gtype) {
        this.gtype = gtype;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
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
