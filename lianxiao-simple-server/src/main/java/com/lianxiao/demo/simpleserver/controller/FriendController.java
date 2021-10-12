package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Apply;
import com.lianxiao.demo.simpleserver.model.Friend;
import com.lianxiao.demo.simpleserver.model.Goods;
import com.lianxiao.demo.simpleserver.service.ApplyService;
import com.lianxiao.demo.simpleserver.service.FriendService;
import com.lianxiao.demo.simpleserver.utils.FastJsonUtils;
import com.lianxiao.demo.simpleserver.utils.IdGeneratorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/friends")
@Api(description = "好友接口")
public class FriendController extends BaseController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private ApplyService applyService;

    @Autowired
    private IdGeneratorUtils idGeneratorUtils;

    @GetMapping(value = "/all")
    @ResponseBody
    @ApiOperation(value = "全部好友", notes = "全部好友")
    public String all(@ApiParam(name = "role_a_id", value = "查找好友的用户id",required = true) @RequestParam(required = true) long role_a_id) {
        List<Friend> results = friendService.showAllFriends(role_a_id);
        return FastJsonUtils.resultSuccess(200, "拉取好友列表成功", results);
    }

    @PostMapping(value = "/request")
    @ResponseBody
    @ApiOperation(value = "发送请求", notes = "发送好友请求")
    public String request(@ApiParam(name = "apply_type", value = "好友请求类型",required = true) @RequestParam int apply_type,
                          @ApiParam(name = "source_uid", value = "发送者id",required = true) @RequestParam long source_uid,
                          @ApiParam(name = "friend_id", value = "被请求者id") @RequestParam(required = false) long friend_id,
                          @ApiParam(name = "group_id", value = "小组id") @RequestParam(required = false) long group_id
                          ) {
        long applyid = idGeneratorUtils.nextId();
        int status = 3;
        Apply apply= new Apply(applyid, apply_type, source_uid, friend_id, group_id,status);
        applyService.addApply(apply);
        Map<String, Object> result = new HashMap<>();
        result.put("result","ok");
        return FastJsonUtils.resultSuccess(200, "发送好友关系成功", result);
    }
    @PostMapping(value = "/search")
    @ResponseBody
    @ApiOperation(value = "查找所有发送请求的请求者", notes = "查找所有发送请求的请求者")
    public String search(@ApiParam(name = "friend_id", value = "被申请用户id",required = true) @RequestParam(required = true) long friend_id
    ) {
        List<Apply> results = applyService.showAllApplies(friend_id);
        return FastJsonUtils.resultSuccess(200, "查询申请者名单成功", results);
    }
    @PostMapping(value = "/pass")
    @ResponseBody
    @ApiOperation(value = "请求通过", notes = "请求通过")
    public String pass(@ApiParam(name = "apply_id", value = "请求id",required = true) @RequestParam long apply_id,
                       @ApiParam(name = "source_uid", value = "发送者id",required = true) @RequestParam long source_uid,
                       @ApiParam(name = "friend_id", value = "被请求者id")  @RequestParam(required = false) long friend_id,
                       @ApiParam(name = "group_id", value = "小组id") @RequestParam(required = false) long group_id
    ){
        long relationid1 = idGeneratorUtils.nextId();
        long relationid2 = idGeneratorUtils.nextId();
        Friend friendrelation1= new Friend(relationid1, source_uid, friend_id);
        Friend friendrelation2= new Friend(relationid2, friend_id, source_uid);
        friendService.addrelation(friendrelation1);
        friendService.addrelation(friendrelation2);
        applyService.updateStatusToPass(apply_id);

        return FastJsonUtils.resultSuccess(200, "添加好友请求通过", null);
    }

    @PostMapping(value = "/reject")
    @ResponseBody
    @ApiOperation(value = "拒绝请求", notes = "拒绝请求")
    public String reject(@ApiParam(name = "apply_id", value = "请求id",required = true) @RequestParam long apply_id){
        applyService.updateStatusToReject(apply_id);
        return FastJsonUtils.resultSuccess(200, "拒绝接受好友请求", null);
    }
}
