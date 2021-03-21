package com.lianxiao.demo.simpleserver.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lianxiao.demo.simpleserver.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "`apply`")
public class Apply extends BaseEntity {
    /**
     * applyid
     */
    @Column(name = "`applyid`")
    @JSONField(ordinal = 1)
    private long applyid;
    /**
     * apply_type
     */
    @Column(name = "`apply_type`")
    @JSONField(ordinal = 2)
    private long apply_type; /**
     * source_uid
     */
    @Column(name = "`source_uid`")
    @JSONField(ordinal = 3)
    private long source_uid; /**
     * friend_id
     */
    @Column(name = "`friend_id`")
    @JSONField(ordinal = 4)
    private long friend_id; /**
     * group_id
     */
    @Column(name = "`group_id`")
    @JSONField(ordinal = 5)
    private long group_id; /**
     * status
     */
    @Column(name = "`status`")
    @JSONField(ordinal = 6)
    private long status;

    public Apply(long applyid, long apply_type, long source_uid, long friend_id, long group_id, long status) {
        this.applyid = applyid;
        this.apply_type = apply_type;
        this.source_uid = source_uid;
        this.friend_id = friend_id;
        this.group_id = group_id;
        this.status = status;
    }
    public Apply(){

    }
    public long getApplyid() {
        return applyid;
    }

    public void setApplyid(long applyid) {
        this.applyid= applyid;
    }

    public int getApply_type(int apply_type) {
        return apply_type;
    }

    public void setApply_type(int apply_type) {
        this.apply_type = apply_type;
    }

    public long getSource_uid() {
        return source_uid;
    }

    public void setSource_uid(long source_uid) {
        this.source_uid = source_uid;
    }

    public String getFriend_id(String friend_id) {
        return friend_id;
    }

    public void setFriend_id(long friend_id) {
        this.friend_id = friend_id; }

    public String getGroup_id(String group_id) {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus() {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Apply{" +
                "applyid=" + applyid +
                ", apply_type=" + apply_type +
                ", source_uid=" + source_uid +
                ", friend_id=" + friend_id +
                ", group_id=" + group_id +
                ", status=" + status +
                '}';
    }
}
