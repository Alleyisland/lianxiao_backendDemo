package com.lianxiao.demo.simpleserver.serviceimpl;

import com.lianxiao.demo.simpleserver.base.BaseServiceImpl;
import com.lianxiao.demo.simpleserver.dao.StudentDao;
import com.lianxiao.demo.simpleserver.model.Student;
import com.lianxiao.demo.simpleserver.service.StudentService;
import com.lianxiao.demo.simpleserver.utils.IdGeneratorUtils;
import com.lianxiao.demo.simpleserver.utils.RedisUtils;
import com.lianxiao.demo.simpleserver.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import com.space.movie.moviespacesimpleserver.contants.Constant;
//import com.space.movie.moviespacesimpleserver.util.EncryptUtil;
//import com.space.movie.moviespacesimpleserver.util.FastJsonUtils;

@Service
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {

    private static final String  INTEREST_TAG = "INTEREST_TAGS_";
    private static final Object ONOFFLINE_FILTER = "ONOFFLINE_FILTER";
    @Resource
    private StudentDao studentDao;

    @Autowired
    private IdGeneratorUtils idGeneratorUtils;

    @Autowired
    private RedisUtils redisUtils;

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

    public void updateInterestTags(long uid, Set<String> tags) {
        String key=INTEREST_TAG+uid;
        redisUtils.flushSet(key,tags);
    }

    public Set<String> commonInterestTags(long uid1, long uid2) {
        String key1=INTEREST_TAG+uid1;
        String key2=INTEREST_TAG+uid2;
        return redisUtils.interSet(key1,key2);
    }

    public Set<String> fetchInterestTags(long uid) {
        String key=INTEREST_TAG+uid;
        return redisUtils.fetchSet(key);
    }

    public void online(long uid){
        redisUtils.bitSetPut(ONOFFLINE_FILTER, uid);
    }

    public void offline(long uid){
        redisUtils.bitSetDel(ONOFFLINE_FILTER, uid);
    }

    @Override
    public Map<Long, Boolean> onlineStatus(Set<Long> uids) {
        return redisUtils.bitSetScan(ONOFFLINE_FILTER,uids);
    }
}
