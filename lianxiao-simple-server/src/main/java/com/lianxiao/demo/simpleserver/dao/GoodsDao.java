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

    void deleteById(long gid);
}
