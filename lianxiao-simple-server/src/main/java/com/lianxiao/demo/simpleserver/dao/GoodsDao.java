package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Goods;
import com.lianxiao.demo.simpleserver.utils.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsDao extends MyMapper<Goods> {

    @Select(value = "select * from goods")
    List<Goods> selectAll();

    @Select(value = "select * from goods where gtype=#{gtype}")
    List<Goods> selectByType(@Param("gtype") int gtype);

    @Select(value = "select * from goods where gid=#{gid}")
    List<Goods> selectByGid(long gid);

    @Insert(value = "INSERT INTO goods (gid,gtype,uid,gname,gdescription,price,picuri) value (#{gid},#{gtype},#{uid},#{gname},#{gdescription},#{price},#{picuri})")
    void insertGoods(Goods goods);

    @Delete(value = "DELETE FROM goods where gid=#{gid}")
    void deleteById(long gid);

    //  模糊查询
    List<Goods> selectByName(@Param("gname") String gname);

    @Select(value = "select * from goods where uid=#{uid}")
    List<Goods> selectByUid(long uid);
}
