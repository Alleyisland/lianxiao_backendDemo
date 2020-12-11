package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.model.Myreply;
import com.lianxiao.demo.simpleserver.service.MyreplyService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/myreply")
public class MyreplyController extends CommonController {
    @Autowired
    private MyreplyService myreplyService;

    @GetMapping("/list")
    public String list(HttpServletRequest request) {
        List<Myreply> result=myreplyService.showAllMyreply();
        //System.out.println(result.size());
        return FastJsonUtils.resultSuccess(200, "拉取列表成功", result);
    }
    @ResponseBody
    @GetMapping("/commit")
    public String commit(HttpServletRequest request, @RequestParam Integer rid,@RequestParam Integer uid,@RequestParam String content) {

        Myreply myreply=new Myreply(rid,uid,content);
        myreplyService.addReply(myreply);
        return FastJsonUtils.resultSuccess(200, "回复成功", myreply);
    }


}
