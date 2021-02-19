package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Reply;
import com.lianxiao.demo.simpleserver.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ReplyDao extends MyMapper<Reply> {

    @Select(value = "select * from reply")
    List<Reply> selectAll();

    void insertReply(Reply Reply);
    void deleteReply(long rid);

    @Select(value = "select * from reply where rid=#{rid}")
    List<Reply> selectByRid(@Param(value = "rid") Long rid);

    @Select(value = "select * from reply where pid=#{pid}")
    List<Reply> selectByPid(@Param(value = "pid") Long pid);

    @Select(value = "select * from reply where uid=#{uid}")
    List<Reply> selectByUid(@Param(value = "uid") Long uid);
}
