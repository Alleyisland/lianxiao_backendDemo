package com.lianxiao.demo.simpleserver.serviceimpl;

import com.lianxiao.demo.simpleserver.base.BaseServiceImpl;
import com.lianxiao.demo.simpleserver.dao.ReplyDao;
import com.lianxiao.demo.simpleserver.model.Reply;
import com.lianxiao.demo.simpleserver.service.ReplyService;
import com.lianxiao.demo.simpleserver.utils.IdGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyServiceImpl extends BaseServiceImpl<Reply> implements ReplyService {

    @Resource
    ReplyDao replyDao;

    @Autowired
    private IdGeneratorUtils idGeneratorUtils;

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


    public long addReply(Reply replyInfo) {
        replyInfo.setRid(idGeneratorUtils.nextId());
        replyDao.insertReply(replyInfo);
        return replyInfo.getRid();
    }

    public void deleteReply(long rid) {
        replyDao.deleteReply(rid);
    }

    public Reply searchByRid(Long rid) {
        List<Reply> results = replyDao.selectByRid(rid);
        if (results.size() != 1)
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

    public List<Reply> search(Long rid, Long uid, Long pid) {
        if (rid == null && pid == null && uid == null)
            return new ArrayList<>();
        else if (rid != null) {
            List<Reply> list=new ArrayList<>();
            Reply reply=searchByRid(rid);
            if(reply!=null){
                list.add(reply);
            }
            return list;
        } else if (pid != null)
            return searchByPid(pid);
        else
            return searchByUid(uid);
    }
}
