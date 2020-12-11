package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.base.BaseServiceImpl;
import com.lianxiao.demo.simpleserver.dao.GoodsDao;
import com.lianxiao.demo.simpleserver.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsService extends BaseServiceImpl<Goods> {
    @Resource
    private GoodsDao goodsDao;

    @Override
    public Mapper<Goods> getMapper(){
        return goodsDao;
    }

    /**
     * 查询所有商品
     */
    public List<Goods> showAllGoods(){
        return goodsDao.selectAll();
    }
}
