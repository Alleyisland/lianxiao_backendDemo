package com.lianxiao.demo.simpleserver.base;

import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * 公共控制器
 */
public class BaseController {
    /**
     * 成功测试
     */
    @GetMapping(value = "/success", produces = {"application/json;charset=UTF-8"})
    public String success() {
        return FastJsonUtils.resultSuccess(200, "成功", null);
    }

    /**
     * 失败测试
     */
    @GetMapping(value = "/error", produces = {"application/json;charset=UTF-8"})
    public String error(HttpServletRequest request) {
        return FastJsonUtils.resultError(200, "失败", null);
    }

}