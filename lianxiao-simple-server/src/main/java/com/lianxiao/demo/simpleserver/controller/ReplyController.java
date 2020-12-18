package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Reply;
import com.lianxiao.demo.simpleserver.service.ReplyService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reply")
public class ReplyController extends BaseController {
    @Autowired
    private ReplyService replyService;

    @GetMapping("/list")
    public String list() {
        List<Reply> result = replyService.showAllReply();
        return FastJsonUtils.resultSuccess(200, "拉取回复列表成功", result);
    }

    @GetMapping("/commit")
    public String commit(@RequestParam Integer rid, @RequestParam Integer pid,
                         @RequestParam Integer uid, @RequestParam String content) {

        Reply reply = new Reply(rid, pid, uid, content);
        replyService.addReply(reply);
        return FastJsonUtils.resultSuccess(200, "回复成功", reply);
    }


}
