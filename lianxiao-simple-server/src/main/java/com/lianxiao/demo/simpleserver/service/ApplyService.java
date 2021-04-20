package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.model.Apply;

import java.util.List;

public interface ApplyService {
    List<Apply> showAllApply();

    void addApply(Apply apply);
    void deleteById(long applyid);

    void updateStatusToPass(long applyid);
    void updateStatusToReject(long applyid);
}
