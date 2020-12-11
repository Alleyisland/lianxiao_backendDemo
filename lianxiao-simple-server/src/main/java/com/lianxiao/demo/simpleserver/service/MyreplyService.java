package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.base.BaseServiceImpl;
import com.lianxiao.demo.simpleserver.dao.MyreplyDao;
import com.lianxiao.demo.simpleserver.model.Myreply;
import com.lianxiao.demo.simpleserver.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MyreplyService extends BaseServiceImpl<Myreply> {

    @Resource
    MyreplyDao myreplyDao;

    @Override
    public Mapper<Myreply> getMapper() {
        return myreplyDao;
    }

    /**
     * 查询所有回复
     */
    public List<Myreply> showAllMyreply() {
        return myreplyDao.selectAll();
    }


    public void addReply(Myreply myreply) {
        myreplyDao.insertReply(myreply);
    }
}
