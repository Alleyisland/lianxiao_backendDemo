package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Goods;
import com.lianxiao.demo.simpleserver.service.GoodsService;
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
@RequestMapping("/goods")
@Api(description = "商品接口")
public class GoodsController extends BaseController {
    @Autowired
    private GoodsService goodsService;


    @GetMapping(value = "/all", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "全部商品", notes = "全部商品")
    public String all() {
        List<Goods> result = goodsService.showAllGoods();
        return FastJsonUtils.resultSuccess(200, "拉取商品列表成功", result);
    }


    @GetMapping(value = "/search", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查找商品", notes = "通过商品id/商品类型/商品名字查找商品")
    public String search(@ApiParam(name = "gid", value = "商品id") @RequestParam(required = false) Long gid,
                         @ApiParam(name = "gtype", value = "商品类型") @RequestParam(required = false) Integer gtype,
                         @ApiParam(name = "gname", value = "商品名称") @RequestParam(required = false, defaultValue = "") String gname) {
        List<Goods> results;
        if (gid != null) {
            Goods result = goodsService.searchByGid(gid);
            return FastJsonUtils.resultSuccess(200, "搜索商品成功", result);
        } else if (!gname.equals(""))
            results = goodsService.searchByName(gname);
        else if (gtype != null)
            results = goodsService.searchByType(gtype);
        else
            results = goodsService.showAllGoods();
        return FastJsonUtils.resultSuccess(200, "搜索商品成功", results);
    }

    @ResponseBody
    @PostMapping(value = "/commit", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发布商品", notes = "发布商品")
    public String commit(@ApiParam(name = "gtype", value = "商品类型",required = true) @RequestParam int gtype,
                         @ApiParam(name = "uid", value = "商品所有者id",required = true) @RequestParam long uid,
                         @ApiParam(name = "gname", value = "商品名称",required = true) @RequestParam String gname,
                         @ApiParam(name = "gdescription", value = "商品描述") @RequestParam(required = false, defaultValue = "") String gdescription,
                         @ApiParam(name = "price", value = "价格",required = true) @RequestParam Double price,
                         @ApiParam(name = "pic_uri", value = "图片uri",required = true) @RequestParam String pic_uri) {
        System.out.println(gtype);
        long gid = super.getIdGeneratorUtils().nextId();
        Goods goods = new Goods(gid, gtype, uid, gname, gdescription, price, pic_uri);
        goodsService.addGoods(goods);
        Map<String, Object> result = new HashMap<>();
        result.put("gid", gid);
        return FastJsonUtils.resultSuccess(200, "发布商品成功", result);
    }

    @GetMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "删除商品", notes = "删除商品")
    public String delete(@ApiParam(name = "gid", value = "商品id",required = true) @RequestParam long gid) {
        goodsService.deleteById(gid);
        Map<String, Object> result = new HashMap<>();
        result.put("gid", gid);
        return FastJsonUtils.resultSuccess(200, "删除商品成功", result);
    }
}