package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Reply;
import com.lianxiao.demo.simpleserver.util.MyMapper;

import java.util.List;

public interface ReplyDao extends MyMapper<Reply> {
    List<Reply> selectAll();

    void insertReply(Reply Reply);


}
