package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lianxiao.demo.simpleserver.base.BaseServiceImpl;
//import com.space.movie.moviespacesimpleserver.contants.Constant;
import com.lianxiao.demo.simpleserver.dao.FilmDao;
//import com.space.movie.moviespacesimpleserver.util.EncryptUtil;
//import com.space.movie.moviespacesimpleserver.util.FastJsonUtils;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FilmService extends BaseServiceImpl<Film>{
    @Resource
    private FilmDao filmDao;

    @Override
    public Mapper<Film> getMapper() {
        return filmDao;
    }

    /**
     * 查询时间间隔内的电影
     */
    public List<Film> showFilmByInterval(Integer left, Integer right) {
        return filmDao.selectByYearInterval(left, right);
    }

    //public PageInfo<Film> getFilmList(Film record) {
    //}

}
