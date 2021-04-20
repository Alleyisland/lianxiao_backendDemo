package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.model.Student;

import java.util.List;
import java.util.Set;

public interface StudentService {
    List<Student> showAllStudent();

    long addStudent(Student studentInfo);

    List<Student> searchByUid(long uid);

    List<Student> searchByDescription(String description);

    boolean auth(Student stu);
    List<Student> SelectByPhone(String phone);

    long update(Student newStu);

    String generateJwtToken(String phone);

    String login_v3(Student studentInfo) ;

    List<Student> search(Long uid, String description);

    void updateInterestTags(Long uid, Set<String> tags);

    Set<String> commonInterestTags(Long uid1, Long uid2);

    Set<String> fetchInterestTags(Long uid);
}
