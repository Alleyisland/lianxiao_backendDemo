package com.lianxiao.demo.simpleserver.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lianxiao.demo.simpleserver.base.BaseController;
import com.lianxiao.demo.simpleserver.dto.StudentDto;
import com.lianxiao.demo.simpleserver.dto.MultiStudentDto;
import com.lianxiao.demo.simpleserver.model.Student;
import com.lianxiao.demo.simpleserver.service.IpLimitService;
import com.lianxiao.demo.simpleserver.service.StudentService;
import com.lianxiao.demo.simpleserver.utils.FastJsonUtils;
import com.lianxiao.demo.simpleserver.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 学生控制层
 *
 * @author fang.zhijun
 */
@RestController
@RequestMapping("/student")
@Api(description = "用户接口")
public class StudentController extends BaseController {

    @NacosInjected
    private NamingService namingService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IpLimitService ipLimitService;

    /**
     * 列表
     */
    @GetMapping(value = "/all")
    @ResponseBody
    @ApiOperation(value = "全部用户", notes = "全部用户")
    public String all(){
        List<Student> result = studentService.showAllStudent();
        return FastJsonUtils.resultSuccess(200, "拉取学生列表成功", result);
    }

    @GetMapping(value = "/open/get_instance")
    @ResponseBody
    public List<Instance> getInstance(@RequestParam String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

    @PostMapping(value="/register_or_login/send_auth_code")
    @ResponseBody
    @ApiOperation(value = "获取验证码", notes = "注册、登录获取验证码")
    public String registerOrLoginStepOne(@ApiParam(name = "phone", value = "手机号",required = true)
                                                 @RequestParam String phone) {
        String json=sendVerifyCode(phone);
        Map<String,Object> resMap=FastJsonUtils.getAllInfo(json);
        if(resMap.containsKey("smsId"))
            return FastJsonUtils.resultSuccess(200, "验证码发送成功", null);
        else return FastJsonUtils.resultSuccess(200, "验证码发送失败", null);
    }

    @PostMapping(value = "/register_or_login/verify_auth_code")
    @ResponseBody
    @ApiOperation(value = "验证验证码", notes = "验证验证码")
    public String registerOrLoginStepTwo(@ApiParam(name = "phone", value = "手机号",required = true)@RequestParam String phone,
                                             @ApiParam(name = "code", value = "验证码",required = true)@RequestParam String code) {
        boolean flag=verifyCode(phone,code);
        Map<String,Object> map=new HashMap<>();
        if(flag){
            map.put("result",true);
            //登录
            if(studentService.SelectByPhone(phone).size()==1) {
                Student stu=studentService.SelectByPhone(phone).get(0);
                String token=TokenUtils.generateJwtToken(phone);
                map.put("uid",stu.getUid());
                map.put("token",token);
                return FastJsonUtils.resultSuccess(200, "验证通过,用户登录成功", map);
            }
            //注册
            else{
                Student stuInfo=new Student();
                stuInfo.setPhone(phone);
                long uid=studentService.addStudent(stuInfo);//插入
                String token=TokenUtils.generateJwtToken(phone);
                map.put("uid",uid);
                map.put("token",token);
                return FastJsonUtils.resultSuccess(200, "验证通过,用户注册成功", map);
            }
        }
        //验证码错误
        else {
            map.put("result",false);
            return FastJsonUtils.resultSuccess(200, "验证码错误，用户注册/登录失败", map);
        }
    }

    public boolean verifyCode(String phone,String code){
        String uri = "https://api2.bmob.cn/1/verifySmsCode/"+code;
        Map<String, String> map = new HashMap<>();
        map.put("mobilePhoneNumber", phone);

        try {
            ResponseEntity<String> apiResponse = restTemplate.postForEntity
                    (
                            uri,
                            generatePostJson(map),
                            String.class
                    );
            if (apiResponse.getStatusCode().value() == 200) {
                String json = apiResponse.getBody();
                Map<String, Object> resMap = FastJsonUtils.getAllInfo(json);
                return resMap.get("msg").equals("ok");
            }
        }
        catch(Exception e){
            return false;
        }
        return false;
    }

    public String sendVerifyCode(String phone){
        String uri = "https://api2.bmob.cn/1/requestSmsCode";

        Map<String, String> map = new HashMap<>();
        map.put("mobilePhoneNumber", phone);

        ResponseEntity<String> apiResponse = restTemplate.postForEntity
                (
                        uri,
                        generatePostJson(map),
                        String.class
                );
        return apiResponse.getBody();
    }

    public HttpEntity<Map<String, String>> generatePostJson(Map<String, String> jsonMap) {

        //如果需要其它的请求头信息、都可以在这里追加
        HttpHeaders httpHeaders = new HttpHeaders();

        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");

        httpHeaders.setContentType(type);
        httpHeaders.set("X-Bmob-Application-Id","f397a2b89250b22bd4482c8a19adcb20");
        httpHeaders.set("X-Bmob-REST-API-Key","3c61f80666e631db54adb751658ce775");

        return new HttpEntity<>(jsonMap, httpHeaders);
    }

//    @PostMapping(value = "/login/v2")
//    @ResponseBody
//    @ApiOperation(value = "登录_v2", notes = "登录_v2")
//    public String login(@ApiParam(name = "phone", value = "手机号",required = true)@RequestParam String phone,
//                         @ApiParam(name = "password", value = "密码",required = true)@RequestParam String password) {
//        Student stu=new Student();
//        stu.setPhone(phone);
//        stu.setPassword(password);
//        if(studentService.auth(stu)) {
//            String token=TokenUtils.generateJwtToken(phone);
//            Map<String,Object> map=new HashMap<>();
//            map.put("token",token);
//            return FastJsonUtils.resultSuccess(200, "用户登录成功", map);
//        }
//        else
//            return FastJsonUtils.resultSuccess(200, "用户登录失败", null);
//    }

    @PostMapping(value = "/login/v3")
    @ResponseBody
    public String login_v3(@RequestBody Student studentInfo){
        String token=studentService.login_v3(studentInfo);
        if(token!=null) {
            Map<String,Object> map=new HashMap<>();
            map.put("token",token);
            return FastJsonUtils.resultSuccess(200, "用户登录成功", map);
        }
        else
            return FastJsonUtils.resultSuccess(200, "用户登录失败", null);
    }

    @PostMapping(value = "/update_info")
    @ResponseBody
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    public String updateInfo(@RequestBody Student newStuInfo) {
        long uid=studentService.update(newStuInfo);
        Map<String,Object> map=new HashMap<>();
        map.put("uid",uid);
        return FastJsonUtils.resultSuccess(200, "修改成功", map);
    }

    @GetMapping("/open/search")
    @ResponseBody
    @ApiOperation(value = "查找用户", notes = "根据用户id/用户描述查找用户")
    public String search(@ApiParam(name = "uid", value = "用户id")@RequestParam(required = false) Long uid,
                         @ApiParam(name = "description", value = "用户描述")@RequestParam(required = false, defaultValue = "") String description) {
        if (uid == null && description.equals(""))
            return FastJsonUtils.resultSuccess(200, "请输入查询条件", null);
        List<Student> results=studentService.search(uid,description);
        return FastJsonUtils.resultSuccess(200, "用户查询成功", results);
    }

    @PostMapping(value = "/update_interest_tags")
    @ResponseBody
    @ApiOperation(value = "更新用户兴趣标签", notes = "更新用户兴趣标签")
    public String updateInterestTag(@RequestBody StudentDto stuInfo) {
        long uid=stuInfo.getUid();
        Set<String> tags=stuInfo.getTags();
        studentService.updateInterestTags(uid,tags);
        Map<String,Object> map=new HashMap<>();
        map.put("status","ok");
        return FastJsonUtils.resultSuccess(200, "更新兴趣标签成功", map);
    }

    @GetMapping(value = "/common_interest_tags")
    @ResponseBody
    @ApiOperation(value = "获取共同兴趣标签", notes = "获取共同兴趣标签")
    public String commonInterestTag(@RequestParam Long uid1,@RequestParam Long uid2) {
        Set<String> results=studentService.commonInterestTags(uid1,uid2);
        return FastJsonUtils.resultSuccess(200, "获取共同兴趣标签成功", results);
    }

    @GetMapping(value = "/open/interest_tags")
    @ResponseBody
    @ApiOperation(value = "查看用户兴趣标签", notes = "查看用户兴趣标签")
    public String commonInterestTag(@RequestParam Long uid) {
        Set<String> results=studentService.fetchInterestTags(uid);
        return FastJsonUtils.resultSuccess(200, "获取兴趣标签成功", results);
    }

    @PostMapping(value = "/open/online")
    @ResponseBody
    @ApiOperation(value = "上线", notes = "更新用户登录状态")
    public String Online(@RequestParam long uid) {
        studentService.online(uid);
        Map<String,Object> map=new HashMap<>();
        map.put("status","ok");
        return FastJsonUtils.resultSuccess(200, "更新在线状态成功", map);
    }

    @PostMapping(value = "/open/offline")
    @ResponseBody
    @ApiOperation(value = "离线", notes = "更新用户登录状态")
    public String Offline(@RequestParam long uid) {
        studentService.offline(uid);
        Map<String,Object> map=new HashMap<>();
        map.put("status","ok");
        return FastJsonUtils.resultSuccess(200, "更新在线状态成功", map);
    }

    @PostMapping(value = "/open/online_status")
    @ResponseBody
    @ApiOperation(value = "查看在线状态", notes = "查看用户在线状态")
    public String onlineStatus(@RequestBody MultiStudentDto multiStudentDto) {
        Set<Long> uids=multiStudentDto.getUids();
        Map<Long,Boolean> statusMap=studentService.onlineStatus(uids);
        return FastJsonUtils.resultSuccess(200, "查询在线状态成功", statusMap);
    }

    @GetMapping(value = "/open/ip_limit")
    @ResponseBody
    @ApiOperation(value = "查看在线状态", notes = "查看用户在线状态")
    public String Ip() {
        boolean res=ipLimitService.validate("127.0.0.1");
        return FastJsonUtils.resultSuccess(200, "ip限流成功", res);
    }
}
