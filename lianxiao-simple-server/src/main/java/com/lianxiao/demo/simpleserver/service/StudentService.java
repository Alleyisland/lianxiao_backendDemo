package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.model.Student;

import java.util.List;
import java.util.Map;
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

    void updateInterestTags(long uid, Set<String> tags);

    Set<String> commonInterestTags(long uid1, long uid2);

    Set<String> fetchInterestTags(long uid);

    void online(long uid);

    void offline(long uid);

    Map<Long, Boolean> onlineStatus(Set<Long> uids);
}
