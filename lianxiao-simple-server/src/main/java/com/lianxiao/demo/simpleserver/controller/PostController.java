package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.model.Post;
import com.lianxiao.demo.simpleserver.service.ElasticService;
import com.lianxiao.demo.simpleserver.service.PostService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import com.lianxiao.demo.simpleserver.util.IdGeneratorUtils;
import io.swagger.annotations.ApiOperation;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private IdGeneratorUtils idGeneratorUtils;

    @ApiOperation(value = "成功测试", httpMethod = "GET")
    @RequestMapping(value = "/success", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String success(HttpServletRequest request) {
        return FastJsonUtils.resultSuccess(200,"成功",null);
    }

    @GetMapping("/init")
    public void init(){
        List<Post> list =new ArrayList<>();
        list.add(new Post(1L,1,"标题1","内容1"));
        list.add(new Post(2L,2,"标题2","内容2"));
        list.add(new Post(3L,2,"标题3","内容3"));
        postService.saveAll(list);
    }

    @ApiOperation(value = "列表", httpMethod="POST")
    @RequestMapping(value = "/submit", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String submit(HttpServletRequest request,@RequestBody Map<String,Object> params){
        Integer type = (Integer) params.get("type");
        String title = (String) params.get("title");
        String content = (String) params.get("content");

        System.out.println(idGeneratorUtils.nextId());
        Long postId=Long.parseLong(idGeneratorUtils.nextId());
        System.out.println(postId);
        Post post=new Post(postId,type,title,content);
        postService.save(post);
        return FastJsonUtils.resultSuccess(200,"成功",post);
    }

    @ApiOperation(value = "查询", httpMethod="GET")
    @RequestMapping(value = "/search", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String search(HttpServletRequest request,@RequestParam(required = false) String keyword){

        Iterator<Post> result = postService.query(keyword).iterator();
        return FastJsonUtils.resultSuccess(200,"成功",result);
    }

    @GetMapping("/all")
    public String all(){
        Iterator<Post> result = postService.findAll();
        return FastJsonUtils.resultSuccess(200,"成功",result);
    }

}