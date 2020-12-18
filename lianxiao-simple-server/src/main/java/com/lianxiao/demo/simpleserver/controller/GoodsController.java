package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Goods;
import com.lianxiao.demo.simpleserver.service.GoodsService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {
    @Autowired
    private GoodsService goodsService;


    @GetMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    public String list() {
        List<Goods> result = goodsService.showAllGoods();
        return FastJsonUtils.resultSuccess(200, "拉取列表成功", result);
    }


    @GetMapping(value = "/search", produces = {"application/json;charset=UTF-8"})
    public String search(@RequestParam String gname) {
        List<Goods> result = goodsService.searchByName(gname);
        return FastJsonUtils.resultSuccess(200, "拉取列表成功", result);
    }

    @ResponseBody
    @GetMapping("/commit")
    public String commit(@RequestParam String gid, @RequestParam String gtype, @RequestParam String uid, @RequestParam String gname, @RequestParam String gdescription, @RequestParam Double price, @RequestParam String pic_uri) {

        Goods goods = new Goods(gid, gtype, uid, gname, gdescription, price, pic_uri);
        goodsService.addGoods(goods);
        return FastJsonUtils.resultSuccess(200, "回复成功", goods);
    }
}