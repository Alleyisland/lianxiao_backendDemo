package com.lianxiao.demo.simpleserver.dao;

import com.lianxiao.demo.simpleserver.model.Student;
import com.lianxiao.demo.simpleserver.util.MyMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Collection;
import java.util.List;

public interface StudentDao extends MyMapper<Student> {

    @Select(value = "select * from student")
    List<Student> selectAll();

    @Insert("INSERT INTO student (uid,`name`, password, description,phone) Value(#{uid},#{name},#{password},#{description},#{phone})")
    void insertStudent(Student student);

    @Select(value = "select * from student where uid=#{uid}")
    List<Student> selectByUid(@Param(value = "uid") long uid);

    @Select(value = "select * from student where phone=#{phone}")
    List<Student> selectByPhone(String phone);

    @Select(value = "select * FROM student s where s.phone =#{phone} and s.password=#{password}")
    List<Student> selectByPhoneAndPassword(Student stu);

    @Update(value = "update student set name=#{name}, password=#{password}, description=#{description} where uid=#{uid}")
    void updateByUid(Student newStu);

    //  模糊查询
    List<Student> selectByDescription(String description);
    //List<Student> selectByYearInterval(@Param("left") Integer left,@Param("right") Integer right);
}
