package com.lianxiao.demo.simpleserver.model;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONType;
import com.lianxiao.demo.simpleserver.base.BaseEntity;

@Table(name = "`student`")
@JSONType(orders={"uid","password","description","phone"})
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

    /**
     *电话
     */
    @Column(name = "`phone`")
    private String phone;

    public Student(String uid, String password, String description,String phone) {
        this.uid = uid;
        this.password = password;
        this.description = description;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}