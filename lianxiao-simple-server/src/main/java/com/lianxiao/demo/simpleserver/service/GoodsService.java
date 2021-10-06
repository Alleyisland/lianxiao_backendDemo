package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.model.Goods;

import java.util.ArrayList;
import java.util.List;

public interface GoodsService {
    public List<Goods> showAllGoods();

    public long addGoods(Goods goodsInfo);
    /**
     * 根据名字查询商品
     */
    public List<Goods> searchByName(String gname);

    public List<Goods> searchByType(int gtype);

    public List<Goods> searchByUid(long uid);

    /**
     * 根据gid删除商品
     */
    public void deleteById(long gid);

    public Goods searchByGid(long gid);

    public List<Goods> search(Long gid, String gname, Integer gtype);
}
