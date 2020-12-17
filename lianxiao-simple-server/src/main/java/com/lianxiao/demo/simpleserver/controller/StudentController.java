package com.lianxiao.demo.simpleserver.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lianxiao.demo.simpleserver.model.Myreply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lianxiao.demo.simpleserver.service.StudentService;
import com.lianxiao.demo.simpleserver.model.Student;
import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 学生控制层
 * @author fang.zhijun
 */
@RestController
@RequestMapping("/student")
@Api(value = "StudentController")
public class StudentController extends CommonController {
    @Autowired
    private StudentService studentService;

    /**
     * 成功测试
     */
    @ApiOperation(value = "成功测试", httpMethod="GET")
    @RequestMapping(value = "/success", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String success(HttpServletRequest request) {
        return FastJsonUtils.resultSuccess(200, "成功", null);
    }

    /**
     * 失败测试
     */
    @ApiOperation(value = "失败测试", httpMethod="GET")
    @RequestMapping(value = "/error", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String error(HttpServletRequest request) {
        return FastJsonUtils.resultError(200, "失败", null);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表", httpMethod="GET")
    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin("http://localhost:8080")
    @ResponseBody
    public String index(HttpServletRequest request) {
        List<Student> result=studentService.showAllStudent();
        //System.out.println(result.size());
        return FastJsonUtils.resultSuccess(200, "拉取列表成功", result);
    }

    @ResponseBody
    @GetMapping("/commit")
    public String commit(HttpServletRequest request, @RequestParam String uid,@RequestParam String password,@RequestParam String description,@RequestParam String phone) {

        Student student=new Student(uid,password,description,phone);
        studentService.addStudent(student);
        return FastJsonUtils.resultSuccess(200, "注册成功", student);
    }


}
