package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.model.Ad;
import com.lianxiao.demo.simpleserver.service.AdService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/ad")
public class AdController extends CommonController{

    @Autowired
    private AdService adService;

    @ApiOperation(value = "成功测试", httpMethod = "GET")
    @RequestMapping(value = "/success", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String success(HttpServletRequest request) {
        return FastJsonUtils.resultSuccess(200,"成功",null);
    }

    @ApiOperation(value = "列表测试", httpMethod = "GET")
    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin("http://localhost:8080")
    @ResponseBody
    public String list(HttpServletRequest request){
        List<Ad> result = adService.showAllAd();
        System.out.println(result);
        return FastJsonUtils.resultSuccess(200,"拉取列表成功",result);
    }
}
