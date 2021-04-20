package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.model.Reply;

import java.util.List;

public interface ReplyService {
    List<Reply> showAllReply();


    long addReply(Reply replyInfo);

    void deleteReply(long rid);

    Reply searchByRid(Long rid);

    List<Reply> searchByPid(Long pid);

    List<Reply> searchByUid(Long uid);

    List<Reply> search(Long rid, Long uid, Long pid);
}
