package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Student;
import com.lianxiao.demo.simpleserver.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentDao extends MyMapper<Student> {
    List<Student> selectAll();
    void insertStudent(Student student);
    //List<Student> selectByYearInterval(@Param("left") Integer left,@Param("right") Integer right);
}
