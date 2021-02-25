package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Reply;
import com.lianxiao.demo.simpleserver.service.ReplyService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reply")
@Api(description = "回复接口")
public class ReplyController extends BaseController {
    @Autowired
    private ReplyService replyService;

    @GetMapping(value = "/all", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "全部回复", notes = "全部回复")
    public String all() {
        List<Reply> result = replyService.showAllReply();
        return FastJsonUtils.resultSuccess(200, "拉取回复列表成功", result);
    }

    @PostMapping(value = "/commit", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发表回复", notes = "发表回复")
    public String commit(@ApiParam(name = "pid", value = "帖子id",required = true) @RequestParam long pid,
                         @ApiParam(name = "uid", value = "发表者id",required = true) @RequestParam long uid,
                         @ApiParam(name = "content", value = "回复内容",required = true) @RequestParam String content) {
        long rid = super.getIdGeneratorUtils().nextId();
        Reply reply = new Reply(rid, pid, uid, content);
        replyService.addReply(reply);
        Map<String, Object> result = new HashMap<>();
        result.put("rid", rid);
        return FastJsonUtils.resultSuccess(200, "回复成功", result);
    }

    @GetMapping(value = "/search", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查找回复", notes = "根据回复id/用户id/帖子id查找回复")
    public String search(@ApiParam(name = "rid", value = "回复id") @RequestParam(required = false) Long rid,
                         @ApiParam(name = "uid", value = "发表者id") @RequestParam(required = false) Long uid,
                         @ApiParam(name = "pid", value = "帖子id") @RequestParam(required = false) Long pid) {
        List<Reply> results;
        if (rid == null && pid == null && uid == null)
            return FastJsonUtils.resultSuccess(200, "请输入查询条件", null);
        else if (rid != null) {
            Reply result = replyService.searchByRid(rid);
            return FastJsonUtils.resultSuccess(200, "查询回复成功", result);
        } else if (pid != null)
            results = replyService.searchByPid(pid);
        else
            results = replyService.searchByUid(uid);
        return FastJsonUtils.resultSuccess(200, "查询回复成功", results);
    }

    @GetMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "删除回复", notes = "删除回复")
    public String delete(@ApiParam(name = "rid", value = "回复id",required = true) @RequestParam long rid) {
        replyService.deleteReply(rid);
        Map<String, Object> result = new HashMap<>();
        result.put("rid", rid);
        return FastJsonUtils.resultSuccess(200, "删除回复成功", result);
    }
}
