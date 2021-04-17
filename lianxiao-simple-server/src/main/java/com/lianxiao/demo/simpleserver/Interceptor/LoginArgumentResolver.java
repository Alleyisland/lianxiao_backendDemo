package com.lianxiao.demo.simpleserver.Interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.lianxiao.demo.simpleserver.dto.StudentDto;
import com.lianxiao.demo.simpleserver.model.Student;
import com.lianxiao.demo.simpleserver.util.TokenUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //  对于StudentDto类型生效
        return methodParameter.getParameterType().equals(StudentDto.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        String token = nativeWebRequest.getHeader("token");
        Map<String,Claim> verify = TokenUtils.verify(token);
        assert verify != null;
        StudentDto stuDto=new StudentDto();
        stuDto.setPhone(verify.get("phone").asString());
        stuDto.setPhone(verify.get("password").asString());
        return stuDto;
    }
}