package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Ad;
import com.lianxiao.demo.simpleserver.service.AdService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ad")
@Api(description = "广告接口")
public class AdController extends BaseController {

    @Autowired
    private AdService adService;

    /**
     * 列表测试
     */
    @GetMapping(value = "/all", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "全部广告", notes = "全部广告")
    public String all() {
        List<Ad> result = adService.showAllAd();
        return FastJsonUtils.resultSuccess(200, "拉取广告列表成功", result);
    }

    @GetMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "删除广告", notes = "删除广告")
    public String delete(@ApiParam(name = "aid", value = "广告id",required = true) @RequestParam long aid) {
        adService.deleteAd(aid);
        Map<String, Object> result = new HashMap<>();
        result.put("aid", aid);
        return FastJsonUtils.resultSuccess(200, "删除广告成功", result);
    }
}
