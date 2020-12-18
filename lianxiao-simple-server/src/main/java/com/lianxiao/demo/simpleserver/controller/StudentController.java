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
        return FastJsonUtils.resultSuccess(200, "拉取列表成功", result);
    }

    @ResponseBody
    @GetMapping("/commit")
    public String commit(@RequestParam String uid, @RequestParam String password, @RequestParam String description, @RequestParam String phone) {

        Student student = new Student(uid, password, description, phone);
        studentService.addStudent(student);
        return FastJsonUtils.resultSuccess(200, "注册成功", student);
    }


}
