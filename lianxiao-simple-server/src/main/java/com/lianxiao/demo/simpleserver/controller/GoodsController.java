package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Goods;
import com.lianxiao.demo.simpleserver.service.GoodsService;
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
@RequestMapping("/goods")
@Api(description = "商品接口")
public class GoodsController extends BaseController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping(value = "/all")
    @ResponseBody
    @ApiOperation(value = "全部商品", notes = "全部商品")
    public String all() {
        List<Goods> result = goodsService.showAllGoods();
        return FastJsonUtils.resultSuccess(200, "拉取商品列表成功", result);
    }

    @GetMapping(value = "/search")
    @ResponseBody
    @ApiOperation(value = "查找商品", notes = "通过商品id/商品类型/商品名字查找商品")
    public String search(@ApiParam(name = "gid", value = "商品id") @RequestParam(required = false) Long gid,
                         @ApiParam(name = "gtype", value = "商品类型") @RequestParam(required = false) Integer gtype,
                         @ApiParam(name = "gname", value = "商品名称") @RequestParam(required = false, defaultValue = "") String gname) {
        if (gid == null && gtype == null && gname.equals(""))
            return FastJsonUtils.resultSuccess(200, "请输入查询条件", null);
        List<Goods> results=goodsService.search(gid,gname,gtype);
        return FastJsonUtils.resultSuccess(200, "搜索商品成功", results);
    }

    @GetMapping(value = "/user_goods")
    @ResponseBody
    @ApiOperation(value = "查询某个用户的商品", notes = "通过用户id查找商品")
    public String search(@ApiParam(name = "uid", value = "用户id") @RequestParam(required = true) Long uid) {
        if (uid == null)
            return FastJsonUtils.resultSuccess(200, "请输入查询条件", null);
        List<Goods> results=goodsService.searchByUid(uid);
        return FastJsonUtils.resultSuccess(200, "搜索商品成功", results);
    }

    @PostMapping(value = "/commit")
    @ResponseBody
    @ApiOperation(value = "发布商品", notes = "发布商品")
    public String commit(@RequestBody Goods goodsInfo) {
        long gid=goodsService.addGoods(goodsInfo);
        Map<String, Object> result = new HashMap<>();
        result.put("gid", gid);
        return FastJsonUtils.resultSuccess(200, "发布商品成功", result);
    }

    @PostMapping(value = "/delete")
    @ResponseBody
    @ApiOperation(value = "删除商品", notes = "删除商品")
    public String delete(@ApiParam(name = "gid", value = "商品id",required = true) @RequestParam long gid) {
        goodsService.deleteById(gid);
        Map<String, Object> result = new HashMap<>();
        result.put("gid", gid);
        return FastJsonUtils.resultSuccess(200, "删除商品成功", result);
    }
}