package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Film;
import com.lianxiao.demo.simpleserver.service.FilmService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

//import com.github.pagehelper.PageInfo;

/**
 * 电影控制层
 *
 * @author fang.zhijun
 */
@RestController
@RequestMapping("/film")
@ApiIgnore
public class FilmController extends BaseController {
    @Autowired
    private FilmService filmService;

    /**
     * 列表
     */
    @GetMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    public String list(@RequestParam Integer left, @RequestParam Integer right) {
        List<Film> result = filmService.showFilmByInterval(left, right);
        System.out.println(result.size());
        return FastJsonUtils.resultSuccess(200, "拉取列表成功", result);
    }

}
