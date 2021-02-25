package com.lianxiao.demo.simpleserver.base;

import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import com.lianxiao.demo.simpleserver.util.IdGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


/**
 * 公共控制器
 */
public class BaseController {

    @Autowired
    private IdGeneratorUtils idGeneratorUtils;

    public IdGeneratorUtils getIdGeneratorUtils() {
        return idGeneratorUtils;
    }

//    /**
//     * 失败测试
//     */
//    @GetMapping(value = "/error", produces = {"application/json;charset=UTF-8"})
//    public String error() {
//        return FastJsonUtils.resultError(200, "失败", null);
//    }

}