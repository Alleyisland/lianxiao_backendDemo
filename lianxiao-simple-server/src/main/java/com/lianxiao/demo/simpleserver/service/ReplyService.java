package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.base.BaseServiceImpl;
import com.lianxiao.demo.simpleserver.dao.ReplyDao;
import com.lianxiao.demo.simpleserver.model.Reply;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReplyService extends BaseServiceImpl<Reply> {

    @Resource
    ReplyDao replyDao;

    @Override
    public Mapper<Reply> getMapper() {
        return replyDao;
    }

    /**
     * 查询所有回复
     */
    public List<Reply> showAllReply() {
        return replyDao.selectAll();
    }


    public void addReply(Reply reply) {
        replyDao.insertReply(reply);
    }

    public void deleteReply(long rid) {
        replyDao.deleteReply(rid);
    }

    public Reply searchByRid(Long rid) {
        List<Reply> results = replyDao.selectByRid(rid);
        if(results.size()!=1)
            return null;
        else
            return results.get(0);
    }

    public List<Reply> searchByPid(Long pid) {
        return replyDao.selectByPid(pid);

    }

    public List<Reply> searchByUid(Long uid) {
        return replyDao.selectByUid(uid);
    }
}
