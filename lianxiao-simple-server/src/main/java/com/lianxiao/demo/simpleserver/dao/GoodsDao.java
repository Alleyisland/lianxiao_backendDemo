package com.lianxiao.demo.simpleserver.dao;
import com.lianxiao.demo.simpleserver.model.Goods;
import com.lianxiao.demo.simpleserver.util.MyMapper;
import java.util.List;
public interface GoodsDao extends MyMapper<Goods> {
    List<Goods> selectAll();
}
