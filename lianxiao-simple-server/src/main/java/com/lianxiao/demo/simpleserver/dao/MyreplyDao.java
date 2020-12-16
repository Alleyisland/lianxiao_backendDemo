package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Myreply;
import com.lianxiao.demo.simpleserver.util.MyMapper;

import java.util.List;

public interface MyreplyDao extends MyMapper<Myreply> {
    List<Myreply> selectAll();

    void insertReply(Myreply myreply);


}
