package com.lianxiao.demo.simpleserver.serviceimpl;

import com.lianxiao.demo.simpleserver.base.BaseServiceImpl;
import com.lianxiao.demo.simpleserver.dao.GoodsDao;
import com.lianxiao.demo.simpleserver.model.Goods;
import com.lianxiao.demo.simpleserver.service.GoodsService;
import com.lianxiao.demo.simpleserver.utils.IdGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsServiceImpl extends BaseServiceImpl<Goods> implements GoodsService {
    @Resource
    private GoodsDao goodsDao;

    @Override
    public Mapper<Goods> getMapper() {
        return goodsDao;
    }

    @Autowired
    private IdGeneratorUtils idGeneratorUtils;

    /**
     * 查询所有商品
     */
    public List<Goods> showAllGoods() {
        return goodsDao.selectAll();
    }

    public long addGoods(Goods goodsInfo) {
        goodsInfo.setGid(idGeneratorUtils.nextId());
        goodsDao.insertGoods(goodsInfo);
        return goodsInfo.getGid();
    }

    /**
     * 根据名字查询商品
     */
    public List<Goods> searchByName(String gname) {
        return goodsDao.selectByName(gname);
    }

    public List<Goods> searchByType(int gtype) {
        return goodsDao.selectByType(gtype);
    }

    /**
     * 根据gid删除商品
     */
    public void deleteById(long gid) {
        goodsDao.deleteById(gid);
    }

    public Goods searchByGid(long gid) {
        List<Goods> result = goodsDao.selectByGid(gid);
        if (result.size() != 1)
            return null;
        else return result.get(0);
    }

    public List<Goods> search(Long gid, String gname, Integer gtype) {
        if(gid==null&&gname==null&&gtype==null)
            return new ArrayList<>();
        if (gid != null) {
            List<Goods> list=new ArrayList<>();
            Goods goods=searchByGid(gid);
            if (goods!=null){
                list.add(goods);
            }
            return list;
        } else if (!gname.equals(""))
            return searchByName(gname);
        else if (gtype != null)
            return searchByType(gtype);
        else
            return showAllGoods();
    }
}