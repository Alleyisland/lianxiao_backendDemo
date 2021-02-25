package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Post;
import com.lianxiao.demo.simpleserver.service.PostService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/post")
@Api(description = "帖子接口")
public class PostController extends BaseController {

    @Autowired
    private PostService postService;

    @GetMapping("/init")
    @ApiOperation(value = "数据初始化", notes = "数据初始化")
    public void init() {
        List<Post> list = new ArrayList<>();
        list.add(new Post(1L, 1, "标题1", "内容1"));
        list.add(new Post(2L, 2, "标题2", "内容2"));
        list.add(new Post(3L, 2, "标题3", "内容3"));
        postService.saveAll(list);
    }

    @PostMapping(value = "/submit", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发表帖子", notes = "发表帖子")
    public String submit(@RequestBody Map<String, Object> params) {
        Integer type = (Integer) params.get("type");
        String title = (String) params.get("title");
        String content = (String) params.get("content");
        long postId = super.getIdGeneratorUtils().nextId();

        Post post = new Post(postId, type, title, content);
        postService.save(post);
        Map<String, Object> result = new HashMap<>();
        result.put("postId", postId);
        return FastJsonUtils.resultSuccess(200, "发布帖子成功", result);
    }

    @GetMapping(value = "/search", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查找帖子", notes = "根据关键词查找帖子")
    public String search(@ApiParam(name = "keyword", value = "搜索关键词") @RequestParam(required = false) String keyword) {

        Iterator<Post> result = postService.query(keyword).iterator();
        return FastJsonUtils.resultSuccess(200, "搜索帖子成功", result);
    }

    @GetMapping(value = "/all", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "全部帖子", notes = "全部帖子")
    public String all() {
        Iterator<Post> result = postService.findAll();
        return FastJsonUtils.resultSuccess(200, "成功", result);
    }

}
