package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Reply;
import com.lianxiao.demo.simpleserver.service.ReplyService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reply")
public class ReplyController extends BaseController {
    @Autowired
    private ReplyService replyService;

    @GetMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    public String list() {
        List<Reply> result = replyService.showAllReply();
        return FastJsonUtils.resultSuccess(200, "拉取回复列表成功", result);
    }

    @PostMapping(value = "/commit", produces = {"application/json;charset=UTF-8"})
    public String commit(@RequestParam long pid,
                         @RequestParam long uid, @RequestParam String content) {
        long rid=super.getIdGeneratorUtils().nextId();
        Reply reply = new Reply(rid, pid, uid, content);
        replyService.addReply(reply);
        Map<String,Object> result=new HashMap<>();
        result.put("rid",rid);
        return FastJsonUtils.resultSuccess(200, "回复成功", result);
    }


}
