package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Ad;
import com.lianxiao.demo.simpleserver.service.AdService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ad")
public class AdController extends BaseController {

    @Autowired
    private AdService adService;

    /**
     * 列表测试
     */
    @GetMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    public String list() {
        List<Ad> result = adService.showAllAd();
        return FastJsonUtils.resultSuccess(200, "拉取广告列表成功", result);
    }
}
