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
    private long applytype; /**
     * source_uid
     */
    @Column(name = "`source_uid`")
    @JSONField(ordinal = 3)
    private long sourceuid; /**
     * friend_id
     */
    @Column(name = "`friend_id`")
    @JSONField(ordinal = 4)
    private long friendid; /**
     * group_id
     */
    @Column(name = "`grou_id`")
    @JSONField(ordinal = 5)
    private long groupid; /**
     * status
     */
    @Column(name = "`status`")
    @JSONField(ordinal = 6)
    private long status;

    public Apply(long applyid, long applytype, long sourceuid, long friendid, long groupid, long status) {
        this.applyid = applyid;
        this.applytype = applytype;
        this.sourceuid = sourceuid;
        this.friendid = friendid;
        this.groupid = groupid;
        this.status = status;
    }
    public Apply(){

    }

    public long getApplyid() {
        return applyid;
    }

    public void setApplyid(long applyid) {
        this.applyid = applyid;
    }

    public long getApplytype() {
        return applytype;
    }

    public void setApplytype(long applytype) {
        this.applytype = applytype;
    }

    public long getSourceuid() {
        return sourceuid;
    }

    public void setSourceuid(long sourceuid) {
        this.sourceuid = sourceuid;
    }

    public long getFriendid() {
        return friendid;
    }

    public void setFriendid(long friendid) {
        this.friendid = friendid;
    }

    public long getGroupid() {
        return groupid;
    }

    public void setGroupid(long groupid) {
        this.groupid = groupid;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Apply{" +
                "applyid=" + applyid +
                ", apply_type=" + applytype +
                ", source_uid=" + sourceuid +
                ", friend_id=" + friendid +
                ", group_id=" + groupid +
                ", status=" + status +
                '}';
    }
}
