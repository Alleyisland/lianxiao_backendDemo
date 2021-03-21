package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Friend;
import com.lianxiao.demo.simpleserver.util.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FriendDao<Frineds> extends MyMapper<Friend> {
    @Select(value = "select * from friend_relation")
    List<Friend> selectAllrelation();

    void insertrelation(Friend friendrelation);

    void deleteById(long relationid);

    @Select(value = "select * from friend_relation where relationid=#{relationid}")
    List<Frineds> selectByGid(long relationid);
}
