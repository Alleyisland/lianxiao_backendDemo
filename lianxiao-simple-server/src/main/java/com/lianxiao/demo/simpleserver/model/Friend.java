package com.lianxiao.demo.simpleserver.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private long roleAId;
    /**
     * role_b_id
     */
    @Column(name = "`role_b_id`")
    @JSONField(ordinal = 3)
    private long roleBId;

    public Friend(long relationid, long roleAId, long roleBId) {
        this.relationid = relationid;
        this.roleAId = roleAId;
        this.roleBId = roleBId;
    }


    public Friend(){

    }
    public long getRelationid() {
        return relationid;
    }

    public void setRelationid(long relationid) {
        this.relationid = relationid;
    }

    public long getRoleAId() { return roleAId; }

    public void setRoleAId(long roleAId) {
        this.roleAId = roleAId;
    }

    public long getRoleBId() {
        return roleBId;
    }

    public void setRoleBId(long roleBId) {
        this.roleBId = roleBId;
    }
}
