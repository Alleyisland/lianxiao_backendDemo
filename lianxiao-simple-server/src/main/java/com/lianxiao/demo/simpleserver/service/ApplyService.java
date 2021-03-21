package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.base.BaseServiceImpl;
import com.lianxiao.demo.simpleserver.dao.ApplyDao;
import com.lianxiao.demo.simpleserver.model.Apply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ApplyService extends BaseServiceImpl<Apply> {
    @Resource
    private ApplyDao applyDao;

    @Override
    public Mapper<Apply> getMapper() {
        return applyDao;
    }
    public List<Apply> showAllApply() {
        return applyDao.selectAll();
    }

    public void addApply(Apply apply) {
        applyDao.insertApply(apply);
    }
    public void deleteById(long applyid) {
        applyDao.deleteById(applyid);
    }

    public void updateStatusToPass(long applyid) {
        applyDao.updateStatusPass(applyid);
    }
    public void updateStatusToReject(long applyid) {
        applyDao.updateStatusReject(applyid);
    }
}
