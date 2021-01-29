package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Reply;
import com.lianxiao.demo.simpleserver.util.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ReplyDao extends MyMapper<Reply> {

    @Select(value = "select * from reply")
    List<Reply> selectAll();

    void insertReply(Reply Reply);

}
