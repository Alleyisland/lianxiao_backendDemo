package com.lianxiao.demo.simpleserver.serviceimpl;

import com.lianxiao.demo.simpleserver.base.BaseServiceImpl;
import com.lianxiao.demo.simpleserver.dao.FriendDao;
import com.lianxiao.demo.simpleserver.model.Friend;
import com.lianxiao.demo.simpleserver.service.FriendService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;
@Service
public class FriendServiceImpl extends BaseServiceImpl<Friend> implements FriendService {
    @Resource
    private FriendDao friendDao;


    @Override
    public Mapper<Friend> getMapper() {
        return friendDao;
    }

    public List<Friend> showAllFriends(long role_a_id) {
        List<Friend> res=friendDao.selectAllFriend(role_a_id);
        return res;
    }

    public void addrelation(Friend friendrelation) {
        friendDao.insertRelation(friendrelation);
    }
    public void deleteById(long relationid) {
        friendDao.deleteById(relationid);
    }
}
