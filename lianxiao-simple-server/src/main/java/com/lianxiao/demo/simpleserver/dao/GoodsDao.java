package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Goods;
import com.lianxiao.demo.simpleserver.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsDao extends MyMapper<Goods> {

    @Select(value = "select * from goods")
    List<Goods> selectAll();

    void insertGoods(Goods goods);

    List<Goods> selectByName(@Param("gname") String gname);

    @Select(value = "select * from goods where gtype=#{gtype}")
    List<Goods> selectByType(@Param("gtype") int gtype);

    void deleteById(long gid);

    @Select(value = "select * from goods where gid=#{gid}")
    List<Goods> selectByGid(long gid);
}
