package com.lianxiao.demo.simpleserver.dto;

import java.util.Set;

public class MultiStudentDto {
    private Set<Long> uids;

    public Set<Long> getUids() {
        return uids;
    }

    public void setUids(Set<Long> uids) {
        this.uids = uids;
    }
}
