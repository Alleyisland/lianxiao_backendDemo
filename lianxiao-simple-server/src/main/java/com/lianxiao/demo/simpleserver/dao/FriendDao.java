package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Friend;
import com.lianxiao.demo.simpleserver.util.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FriendDao extends MyMapper<Friend> {
    @Select(value = "select * from friend_relation")
    List<Friend> selectAllrelation();

    List<Friend> selectAllFriend(long role_a_id);

    void insertRelation(Friend friendRelation);

    void deleteById(long relationid);

    @Select(value = "select * from friend_relation where relationid=#{relationid}")
    List<Friend> selectByGid(long relationid);
}
