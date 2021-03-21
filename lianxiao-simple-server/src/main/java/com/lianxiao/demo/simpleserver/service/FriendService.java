package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.base.BaseServiceImpl;
import com.lianxiao.demo.simpleserver.dao.FriendDao;
import com.lianxiao.demo.simpleserver.model.Friend;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;
@Service
public class FriendService extends BaseServiceImpl<Friend> {
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
