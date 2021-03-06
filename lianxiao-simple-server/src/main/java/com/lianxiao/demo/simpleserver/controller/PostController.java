package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Post;
import com.lianxiao.demo.simpleserver.service.PostService;
import com.lianxiao.demo.simpleserver.utils.FastJsonUtils;
import com.lianxiao.demo.simpleserver.utils.IdGeneratorUtils;
import com.lianxiao.demo.simpleserver.utils.TransformUtils;
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

    @Autowired
    private IdGeneratorUtils idGeneratorUtils;

    @GetMapping("/init")
    @ResponseBody
    @ApiOperation(value = "数据初始化", notes = "数据初始化")
    public void init() {
//        List<Post> list = new ArrayList<>();
//        list.add(new Post(1L, 1, "标题1", "内容1"));
//        list.add(new Post(2L, 2, "标题2", "内容2"));
//        list.add(new Post(3L, 2, "标题3", "内容3"));
//        postService.saveAll(list);
    }

    @PostMapping(value = "/submit")
    @ResponseBody
    @ApiOperation(value = "发表帖子", notes = "发表帖子")
    public String submit(@ApiParam(name="type",value = "帖子类型",required = true)@RequestParam int type,
                         @ApiParam(name="owner_id",value = "发布者id",required = true)@RequestParam long owner_id,
                         @ApiParam(name="title",value = "帖子标题",required = true)@RequestParam String title,
                         @ApiParam(name="content",value = "帖子内容",required = true)@RequestParam String content
    ) {
        long postId = idGeneratorUtils.nextId();
        Post post = new Post(postId,type,String.valueOf(owner_id),new Date(),title,content);
        postService.save(post);
        Map<String, Object> result = new HashMap<>();
        result.put("postId", postId);
        return FastJsonUtils.resultSuccess(200, "发布帖子成功", result);
    }


    @PostMapping(value = "/thumb_up")
    @ResponseBody
    public String thumbUp(@RequestParam long pid) {
        postService.thumbUp(pid);
        Map<String, Object> result = new HashMap<>();
        result.put("status", "ok");
        return FastJsonUtils.resultSuccess(200, "set成功", result);
    }

    @GetMapping(value = "/get_thumb_up")
    @ResponseBody
    public String getThumbUp(@RequestParam long pid) {
        int cnt=postService.getThumbUp(pid);
        Map<String, Object> result = new HashMap<>();
        result.put("status", "ok");
        result.put("cnt", cnt);
        return FastJsonUtils.resultSuccess(200, "set成功", result);
    }

    @GetMapping(value = "/get_hottest_post")
    @ResponseBody
    public String getHottestPost() {
        Set<Object> results=postService.getTopKPost();
        return FastJsonUtils.resultSuccess(200, "获取排行榜成功", results);
    }

    @GetMapping(value = "/search")
    @ResponseBody
    @ApiOperation(value = "查找帖子", notes = "根据关键词查找帖子")
    public String search(@ApiParam(name = "keyword", value = "搜索关键词") @RequestParam(required = false) String keyword) {
        List<Post> result = TransformUtils.Iter2List(postService.query(keyword).iterator());
        return FastJsonUtils.resultSuccess(200, "搜索帖子成功", result);
    }

    @GetMapping(value = "/all")
    @ResponseBody
    @ApiOperation(value = "全部帖子", notes = "全部帖子")
    public String all() {
        List<Post> results = postService.findAll();
        return FastJsonUtils.resultSuccess(200, "成功", results);
    }

}
