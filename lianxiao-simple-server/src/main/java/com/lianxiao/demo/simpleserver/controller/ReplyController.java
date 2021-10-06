package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Post;
import com.lianxiao.demo.simpleserver.model.Reply;
import com.lianxiao.demo.simpleserver.service.PostService;
import com.lianxiao.demo.simpleserver.service.ReplyService;
import com.lianxiao.demo.simpleserver.utils.FastJsonUtils;
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
    private PostService postService;

    @GetMapping(value = "/all")
    @ResponseBody
    @ApiOperation(value = "全部回复", notes = "全部回复")
    public String all() {
        List<Reply> result = replyService.showAllReply();
        return FastJsonUtils.resultSuccess(200, "拉取回复列表成功", result);
    }

    @PostMapping(value = "/commit")
    @ResponseBody
    @ApiOperation(value = "发表回复", notes = "发表回复")
    public String commit(@RequestBody Reply replyInfo) {
        long rid=replyService.addReply(replyInfo);
        Map<String, Object> result = new HashMap<>();
        result.put("rid", rid);
        return FastJsonUtils.resultSuccess(200, "回复成功", result);
    }

    @GetMapping(value = "/search")
    @ResponseBody
    @ApiOperation(value = "查找回复", notes = "根据回复id/用户id/帖子id查找回复")
    public String search(@ApiParam(name = "rid", value = "回复id") @RequestParam(required = false) Long rid,
                         @ApiParam(name = "uid", value = "发表者id") @RequestParam(required = false) Long uid,
                         @ApiParam(name = "pid", value = "帖子id") @RequestParam(required = false) Long pid) {
        if (uid == null && rid == null && pid == null)
            return FastJsonUtils.resultSuccess(200, "请输入查询条件", null);
        List<Reply> results=replyService.search(rid,uid,pid);
        return FastJsonUtils.resultSuccess(200, "查询回复成功", results);
    }
    @GetMapping(value = "/search_by_id")
    @ResponseBody
    @ApiOperation(value = "根据id查找回复", notes = "根据帖子id查找回复")
    public String searchbyid(@ApiParam(name = "id", value = "根据id搜索回复") @RequestParam(required = true) Long id) {
        List<Reply> results=replyService.searchByPid(id);
        return FastJsonUtils.resultSuccess(200, "搜索回复成功", results);
    }

    @PostMapping(value = "/delete")
    @ResponseBody
    @ApiOperation(value = "删除回复", notes = "删除回复")
    public String delete(@ApiParam(name = "rid", value = "回复id",required = true) @RequestParam long rid) {
        replyService.deleteReply(rid);
        Map<String, Object> result = new HashMap<>();
        result.put("rid", rid);
        return FastJsonUtils.resultSuccess(200, "删除回复成功", result);
    }
}
