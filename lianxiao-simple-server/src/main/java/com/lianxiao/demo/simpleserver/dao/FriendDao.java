package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Friend;
import com.lianxiao.demo.simpleserver.utils.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FriendDao extends MyMapper<Friend> {
    @Select(value = "select * from friend_relation")
    List<Friend> selectAllrelation();

    @Select(value = "select * from friend_relation where role_a_id=#{role_a_id}")
    List<Friend> selectAllFriend(@Param("role_a_id") long roleAId);

    @Insert(value = "INSERT INTO friend_relation(relationid,role_a_id,role_b_id) value (#{relationid},#{roleAId},#{roleBId})")
    void insertRelation(Friend friendRelation);

    @Delete(value = "delete from friend_relation where role_b_id=#{roleBId}")
    void deleteById(long relationid);

    @Select(value = "select * from friend_relation where relationid=#{relationid}")
    List<Friend> selectByGid(long relationid);
}
