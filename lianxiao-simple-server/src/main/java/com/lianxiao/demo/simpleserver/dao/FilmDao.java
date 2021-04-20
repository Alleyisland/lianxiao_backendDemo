package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Film;
import com.lianxiao.demo.simpleserver.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilmDao extends MyMapper<Film> {
    List<Film> selectAll();

    List<Film> selectByYearInterval(@Param("left") Integer left, @Param("right") Integer right);
}
