package com.lianxiao.demo.simpleserver.controller;

import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.model.Student;
import com.lianxiao.demo.simpleserver.service.StudentService;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 学生控制层
 *
 * @author fang.zhijun
 */
@RestController
@RequestMapping("/student")
@Api(description = "用户接口")
public class StudentController extends BaseController {
    @Autowired
    private StudentService studentService;

    /**
     * 列表
     */
    @GetMapping(value = "/all", produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "全部用户", notes = "全部用户")
    public String all(){
        List<Student> result = studentService.showAllStudent();
        return FastJsonUtils.resultSuccess(200, "拉取学生列表成功", result);
    }

    @GetMapping("/register")
    public String register(@RequestParam long uid, @RequestParam String password, @RequestParam String description, @RequestParam String phone) {
        Student student = new Student(uid, password, description, phone);
        studentService.addStudent(student);
        return FastJsonUtils.resultSuccess(200, "注册成功", student);
    }

    @GetMapping("/login_v1")
    @ApiOperation(value = "用户登录_v1", notes = "用户登录_v1")
    public String login(@ApiParam(name = "uid", value = "用户id",required = true)@RequestParam Long uid,
                         @ApiParam(name = "password", value = "密码",required = true)@RequestParam String password) {
        Student stu=new Student();
        stu.setUid(uid);
        stu.setPassword(password);
        if(studentService.auth(stu))
            return FastJsonUtils.resultSuccess(200, "用户登录成功", stu);
        else
            return FastJsonUtils.resultSuccess(200, "用户登录失败", null);
    }

    @GetMapping("/search")
    @ApiOperation(value = "查找用户", notes = "根据用户id/用户描述查找用户")
    public String search(@ApiParam(name = "uid", value = "用户id")@RequestParam(required = false) Long uid,
                         @ApiParam(name = "description", value = "用户描述")@RequestParam(required = false, defaultValue = "") String description) {
        List<Student> students;
        if (uid == null && description.equals(""))
            return FastJsonUtils.resultSuccess(200, "请输入查询条件", null);
        else if (uid != null)
            students = studentService.searchByUid(uid);
        else
            students = studentService.searchByDescription(description);
        return FastJsonUtils.resultSuccess(200, "用户查询成功", students);
    }

}
