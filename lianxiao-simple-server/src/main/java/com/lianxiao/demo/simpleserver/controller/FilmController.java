package com.lianxiao.demo.simpleserver.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import com.github.pagehelper.PageInfo;

import com.lianxiao.demo.simpleserver.service.FilmService;
import com.lianxiao.demo.simpleserver.model.Film;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 电影控制层
 * @author fang.zhijun
 */
@RestController
@RequestMapping("/film")
@Api(value = "FilmController")
public class FilmController extends CommonController {
    @Autowired
    private FilmService filmService;

    /**
     * 成功测试
     */
    @ApiOperation(value = "成功测试", httpMethod="GET")
    @RequestMapping(value = "/success", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String success(HttpServletRequest request) {
        return FastJsonUtils.resultSuccess(200, "成功", null);
    }

    /**
     * 失败测试
     */
    @ApiOperation(value = "失败测试", httpMethod="GET")
    @RequestMapping(value = "/error", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String error(HttpServletRequest request) {
        return FastJsonUtils.resultError(200, "失败", null);
    }

    /**
     * 列表测试
     */
    @ApiOperation(value = "列表测试", httpMethod="GET")
    @RequestMapping(value = "/test_list", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String test_list(HttpServletRequest request) {
        List<Film> data=new ArrayList<>();
        data.add(new Film("name",1999,"me","he","she","中国","-","-"));
        return FastJsonUtils.resultList(200, "测试列表", 1,10,data);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表", httpMethod="GET")
    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin("http://localhost:8080")
    @ResponseBody
    public String index(HttpServletRequest request, @RequestParam Integer left, @RequestParam Integer right) {
        List<Film> result=filmService.showFilmByInterval(left,right);
        System.out.println(result.size());
        return FastJsonUtils.resultSuccess(200, "拉取列表成功", result);
    }

}
