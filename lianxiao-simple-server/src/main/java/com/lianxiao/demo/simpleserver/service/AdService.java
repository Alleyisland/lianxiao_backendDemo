package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.base.BaseServiceImpl;
import com.lianxiao.demo.simpleserver.dao.AdDao;
import com.lianxiao.demo.simpleserver.model.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class AdService extends BaseServiceImpl<Ad> {

    @Autowired
    private AdDao adDao;

    @Override
    public Mapper<Ad> getMapper(){
        return adDao;
    }

    /**
     * 查询所有商品
     */
    public List<Ad> showAllAd(){
        return adDao.selectAll();
    }

}