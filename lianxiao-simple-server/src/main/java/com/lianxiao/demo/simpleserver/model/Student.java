package com.lianxiao.demo.simpleserver.model;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONType;
import com.lianxiao.demo.simpleserver.base.BaseEntity;

@Table(name = "`student`")
@JSONType(orders={"uid","password","description"})
public class Student  extends BaseEntity {

    /**
     * uid
     */
    @Column(name = "`uid`")
    private String uid;

    /**
     * 密码
     */
    @Column(name = "`password`")
    private String password;

    /**
     * 描述
     */
    @Column(name = "`description`")
    private String description;

    public Student(String uid, String password, String description) {
        this.uid = uid;
        this.password = password;
        this.description = description;
    }


    public Student() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}