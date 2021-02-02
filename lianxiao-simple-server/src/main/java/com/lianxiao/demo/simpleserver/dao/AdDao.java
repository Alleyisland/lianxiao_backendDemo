package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Ad;
import com.lianxiao.demo.simpleserver.model.Reply;
import com.lianxiao.demo.simpleserver.util.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdDao extends MyMapper<Ad> {
    @Select(value = "select * from ad")
    List<Ad> selectAll();
    void deleteAd(long aid);
}
