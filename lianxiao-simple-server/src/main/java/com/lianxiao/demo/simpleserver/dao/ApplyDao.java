package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Apply;
import com.lianxiao.demo.simpleserver.util.MyMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ApplyDao extends MyMapper<Apply> {
    @Select(value = "select * from apply")
    List<Apply> selectApply();

    void insertApply(Apply apply);
    void deleteById(long applyid);

    @Update(value = "update apply set status=1 where applyid=#{applyid}")
    void updateStatusPass(long applyid);
}
