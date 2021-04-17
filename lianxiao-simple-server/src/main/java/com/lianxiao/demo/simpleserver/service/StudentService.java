package com.lianxiao.demo.simpleserver.service;

import com.lianxiao.demo.simpleserver.base.BaseServiceImpl;
import com.lianxiao.demo.simpleserver.dao.StudentDao;
import com.lianxiao.demo.simpleserver.model.Student;
import com.lianxiao.demo.simpleserver.util.IdGeneratorUtils;
import com.lianxiao.demo.simpleserver.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

//import com.space.movie.moviespacesimpleserver.contants.Constant;
//import com.space.movie.moviespacesimpleserver.util.EncryptUtil;
//import com.space.movie.moviespacesimpleserver.util.FastJsonUtils;

@Service
public class StudentService extends BaseServiceImpl<Student> {

    @Resource
    private StudentDao studentDao;

    @Autowired
    private IdGeneratorUtils idGeneratorUtils;

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

    public long addStudent(Student studentInfo) {
        studentInfo.setUid(idGeneratorUtils.nextId());
        studentDao.insertStudent(studentInfo);
        return studentInfo.getUid();
    }

    public List<Student> searchByUid(long uid) {
        return studentDao.selectByUid(uid);
    }

    public List<Student> searchByDescription(String description) {
        return studentDao.selectByDescription(description);
    }

    public boolean auth(Student stu) {
        return studentDao.selectByPhoneAndPassword(stu).size()==1;
    }
    public List<Student> SelectByPhone(String phone) {
        return studentDao.selectByPhone(phone);
    }

    public long update(Student newStu) {
        studentDao.updateByUid(newStu);
        return newStu.getUid();
    }
    //public PageInfo<Film> getFilmList(Film record) {
    //}

    public String generateJwtToken(String phone){
        return TokenUtils.generateJwtToken(phone);
    }

    public String login_v3(Student studentInfo) {
        if(auth(studentInfo)) {
            return generateJwtToken(studentInfo.getPhone());
        }
        else{
            return null;
        }
    }

    public List<Student> search(Long uid, String description) {
        if (uid != null)
            return searchByUid(uid);
        else
            return searchByDescription(description);
    }
}
