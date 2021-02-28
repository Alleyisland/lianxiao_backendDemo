package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Student;
import com.lianxiao.demo.simpleserver.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;

public interface StudentDao extends MyMapper<Student> {

    @Select(value = "select * from student")
    List<Student> selectAll();

    void insertStudent(Student student);

    @Select(value = "select * from student where uid=#{uid}")
    List<Student> selectByUid(@Param(value = "uid") long uid);

    List<Student> selectByDescription(String description);

    @Select(value = "select * from student where phone=#{phone}")
    List<Student> selectByPhone(String phone);
    List<Student> selectByUidAndPassword(Student stu);
    //List<Student> selectByYearInterval(@Param("left") Integer left,@Param("right") Integer right);
}
