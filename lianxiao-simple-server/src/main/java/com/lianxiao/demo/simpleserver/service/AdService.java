package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.base.BaseServiceImpl;
import com.lianxiao.demo.simpleserver.dao.AdDao;
import com.lianxiao.demo.simpleserver.model.Ad;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdService extends BaseServiceImpl<Ad> {

    @Resource
    private AdDao adDao;

    @Override
    public Mapper<Ad> getMapper() {
        return adDao;
    }

    /**
     * 查询所有商品
     */
    public List<Ad> showAllAd() {

        return adDao.selectAll();
    }

    public void deleteAd(long aid) {
        adDao.deleteAd(aid);
    }

}
