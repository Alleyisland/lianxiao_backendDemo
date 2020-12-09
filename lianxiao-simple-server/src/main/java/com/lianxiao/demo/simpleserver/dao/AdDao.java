package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Ad;
import com.lianxiao.demo.simpleserver.util.MyMapper;

import java.util.List;

public interface AdDao extends MyMapper<Ad> {
    List<Ad> selectAll();
}
