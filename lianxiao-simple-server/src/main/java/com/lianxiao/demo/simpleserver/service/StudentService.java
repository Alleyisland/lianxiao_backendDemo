package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lianxiao.demo.simpleserver.base.BaseServiceImpl;
//import com.space.movie.moviespacesimpleserver.contants.Constant;
import com.lianxiao.demo.simpleserver.dao.StudentDao;
//import com.space.movie.moviespacesimpleserver.util.EncryptUtil;
//import com.space.movie.moviespacesimpleserver.util.FastJsonUtils;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentService extends BaseServiceImpl<Student>{
    @Resource
    private StudentDao studentDao;

    @Override
    public Mapper<Student> getMapper() {
        return studentDao;
    }

    /**
     * 查询所有学生
     */
    public List<Student> showAllStudent() {
        return studentDao.selectAll();
    }
    public void addStudent(Student student) {
        studentDao.insertStudent(student);

    //public PageInfo<Film> getFilmList(Film record) {
    //}

}
