package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Apply;
import com.lianxiao.demo.simpleserver.util.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ApplyDao extends MyMapper<Apply> {
    @Select(value = "select * from apply")
    List<Apply> selectApply();

    @Insert(value = "insert into apply(applyid,apply_type,source_uid,friend_id,group_id,status) value (#{applyid},#{apply_type},#{source_uid},#{friend_id},#{group_id},#{status})")
    void insertApply(Apply apply);
    @Delete(value = "delete from apply where applyid=#{applyid}")
    void deleteById(long applyid);

    @Update(value = "update apply set status=1 where applyid=#{applyid}")
    void updateStatusPass(long applyid);
    @Update(value = "update apply set status=2 where applyid=#{applyid}")
    void updateStatusReject(long applyid);
}
