package com.lianxiao.demo.simpleserver.controller;

import com.google.inject.internal.asm.$Label;
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

    @GetMapping(value = "/search",produces = {"application/json;charset=UTF-8"})
    public String search(@RequestParam(required = false) Long rid,
                         @RequestParam(required = false) Long uid,
                         @RequestParam(required = false) Long pid){
        List<Reply> results;
        if(rid==null&&pid==null&&uid==null)
            return FastJsonUtils.resultSuccess(200, "请输入查询条件", null);
        else if(rid!=null) {
            Reply result = replyService.searchByRid(rid);
            return FastJsonUtils.resultSuccess(200, "查询回复成功", result);
        }
        else if(pid!=null)
            results=replyService.searchByPid(pid);
        else
            results=replyService.searchByUid(uid);
        return FastJsonUtils.resultSuccess(200, "查询回复成功", results);
    }

    @GetMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    public String delete(@RequestParam long rid) {
        replyService.deleteReply(rid);
        Map<String,Object> result=new HashMap<>();
        result.put("rid",rid);
        return FastJsonUtils.resultSuccess(200, "删除回复成功", result);
    }
}
