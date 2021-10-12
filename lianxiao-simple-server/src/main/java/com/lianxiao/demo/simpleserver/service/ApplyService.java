package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.model.Apply;
import com.lianxiao.demo.simpleserver.model.Friend;
import com.lianxiao.demo.simpleserver.model.Goods;

import java.util.List;

public interface ApplyService {
    List<Apply> showAllApply();

    void addApply(Apply apply);
    void deleteById(long applyid);

    List<Apply> showAllApplies(long friend_id);
    void updateStatusToPass(long applyid);
    void updateStatusToReject(long applyid);

}
