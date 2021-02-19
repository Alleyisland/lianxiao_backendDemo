package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Student;
import com.lianxiao.demo.simpleserver.service.StudentService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生控制层
 *
 * @author fang.zhijun
 */
@RestController
@RequestMapping("/student")
@Api(value = "StudentController")
public class StudentController extends BaseController {
    @Autowired
    private StudentService studentService;

    /**
     * 列表
     */
    @GetMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    public String list() {
        List<Student> result = studentService.showAllStudent();
        return FastJsonUtils.resultSuccess(200, "拉取学生列表成功", result);
    }

    @GetMapping("/commit")
    public String commit(@RequestParam long uid, @RequestParam String password, @RequestParam String description, @RequestParam String phone) {

        Student student = new Student(uid, password, description, phone);
        studentService.addStudent(student);
        return FastJsonUtils.resultSuccess(200, "注册成功", student);
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) Long uid,
                         @RequestParam(required = false,defaultValue = "") String description) {
        List<Student> students;
        if(uid==null&& description.equals(""))
            return FastJsonUtils.resultSuccess(200, "请输入查询条件", null);
        else if(uid!=null)
            students=studentService.searchByUid(uid);
        else
            students=studentService.searchByDescription(description);
        return FastJsonUtils.resultSuccess(200, "用户查询成功", students);
    }


}
