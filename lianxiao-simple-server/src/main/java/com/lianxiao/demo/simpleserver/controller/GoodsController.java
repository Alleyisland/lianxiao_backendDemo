package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Goods;
import com.lianxiao.demo.simpleserver.service.GoodsService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {
    @Autowired
    private GoodsService goodsService;


    @GetMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    public String list() {
        List<Goods> result = goodsService.showAllGoods();
        return FastJsonUtils.resultSuccess(200, "拉取商品列表成功", result);
    }


    @GetMapping(value = "/search", produces = {"application/json;charset=UTF-8"})
    public String search(@RequestParam(required = false) Long gid,
                         @RequestParam(required = false) Integer gtype,
                         @RequestParam(required = false, defaultValue = "") String gname) {
        List<Goods> results;
        if(gid!=null) {
            Goods result = goodsService.searchByGid(gid);
            return FastJsonUtils.resultSuccess(200, "搜索商品成功", result);
        }
        else if(!gname.equals(""))
            results = goodsService.searchByName(gname);
        else if(gtype!=null)
            results = goodsService.searchByType(gtype);
        else
            results = goodsService.showAllGoods();
        return FastJsonUtils.resultSuccess(200, "搜索商品成功", results);
    }

    @ResponseBody
    @PostMapping(value = "/commit", produces = {"application/json;charset=UTF-8"})
    public String commit(@RequestParam int gtype,
                         @RequestParam long uid, @RequestParam String gname,
                         @RequestParam(required = false, defaultValue = "") String gdescription,
                         @RequestParam Double price, @RequestParam String pic_uri)
    {
        System.out.println(gtype);
        long gid = super.getIdGeneratorUtils().nextId();
        Goods goods = new Goods(gid, gtype, uid, gname, gdescription, price, pic_uri);
        goodsService.addGoods(goods);
        Map<String,Object> result=new HashMap<>();
        result.put("gid",gid);
        return FastJsonUtils.resultSuccess(200, "发布商品成功", result);
    }

    @GetMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    public String search(@RequestParam long gid) {
        goodsService.deleteById(gid);
        Map<String,Object> result=new HashMap<>();
        result.put("gid",gid);
        return FastJsonUtils.resultSuccess(200, "删除商品成功", result);
    }
}