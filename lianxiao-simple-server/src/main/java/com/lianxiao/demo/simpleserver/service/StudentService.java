package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.model.Student;

import java.util.List;

public interface StudentService {
    public List<Student> showAllStudent();

    public long addStudent(Student studentInfo);

    public List<Student> searchByUid(long uid);

    public List<Student> searchByDescription(String description);

    public boolean auth(Student stu);
    public List<Student> SelectByPhone(String phone);

    public long update(Student newStu);

    public String generateJwtToken(String phone);

    public String login_v3(Student studentInfo) ;

    public List<Student> search(Long uid, String description);
}
