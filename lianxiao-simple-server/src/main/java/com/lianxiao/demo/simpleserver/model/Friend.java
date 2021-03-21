package com.lianxiao.demo.simpleserver.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lianxiao.demo.simpleserver.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "`friend_relation`")
public class Friend extends BaseEntity {
    /**
     * relationid
     */
    @Column(name = "`relationid`")
    @JSONField(ordinal = 1)
    private long relationid;
    /**
     * role_a_id
     */
    @Column(name = "`role_a_id`")
    @JSONField(ordinal = 2)
    private long role_a_id;
    /**
     * role_b_id
     */
    @Column(name = "`role_b_id`")
    @JSONField(ordinal = 3)
    private long role_b_id;

    public Friend(long relationid, long role_a_id, long role_b_id) {
        this.relationid = relationid;
        this.role_a_id = role_a_id;
        this.role_b_id = role_b_id;
    }


    public Friend(){

    }
    public long getRelationid() {
        return relationid;
    }

    public void setRelationid(long relationid) {
        this.relationid = relationid;
    }

    public int getRole_a_id(int role_a_id) { return role_a_id; }

    public void setRole_a_id(int role_a_id) {
        this.role_a_id = role_a_id;
    }

    public long getRole_b_id(int role_b_id) {
        return role_b_id;
    }

    public void setRole_b_id(int role_b_id) {
        this.role_b_id = role_b_id;
    }

    @Override
    public String toString() {
        return "FriendRelation{" +
                "relationid=" + relationid +
                ", role_a_id=" + role_a_id +
                ", role_b_id=" + role_b_id +
                '}';
    }
}
