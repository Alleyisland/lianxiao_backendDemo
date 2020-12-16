package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.model.Goods;
import com.lianxiao.demo.simpleserver.service.GoodsService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController extends CommonController {
    @Autowired
    private GoodsService goodsService;

    /**
     成功测试
     */
    @ApiOperation(value = "成功测试", httpMethod = "GET")
    @RequestMapping(value = "/success", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String success(HttpServletRequest request) {
        return FastJsonUtils.resultSuccess(200,"成功",null);
    }

    /**
     失败测试
     */
    @ApiOperation(value = "失败测试", httpMethod = "GET")
    @RequestMapping(value = "/error", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String error(HttpServletRequest request) {
        return FastJsonUtils.resultError(200,"失败",null);
    }


    @ApiOperation(value = "列表测试", httpMethod = "GET")
    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin("http://localhost:8080")
    @ResponseBody
    public String list(HttpServletRequest request){
        List<Goods> result = goodsService.showAllGoods();
        return FastJsonUtils.resultSuccess(200,"拉取列表成功",result);
    }

    @ApiOperation(value = "列表测试", httpMethod = "POST")
    @RequestMapping(value = "/search", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin("http://localhost:8080")
    @ResponseBody
    public String search(HttpServletRequest request, @RequestParam String gname){
        List<Goods> result = goodsService.searchByName(gname);
        return FastJsonUtils.resultSuccess(200,"拉取列表成功",result);
    }
}