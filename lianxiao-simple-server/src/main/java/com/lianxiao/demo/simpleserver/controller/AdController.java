package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Ad;
import com.lianxiao.demo.simpleserver.service.AdService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    public String delete(@RequestParam long aid) {
        adService.deleteAd(aid);
        Map<String,Object> result=new HashMap<>();
        result.put("aid",aid);
        return FastJsonUtils.resultSuccess(200, "删除广告成功", result);
    }
}
