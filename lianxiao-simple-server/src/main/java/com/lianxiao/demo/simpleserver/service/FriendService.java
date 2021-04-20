package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.base.BaseServiceImpl;
import com.lianxiao.demo.simpleserver.dao.FriendDao;
import com.lianxiao.demo.simpleserver.model.Friend;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;
public interface FriendService{

    List<Friend> showAllFriends(long role_a_id);

    void addrelation(Friend friendrelation);
    void deleteById(long relationid);
}
