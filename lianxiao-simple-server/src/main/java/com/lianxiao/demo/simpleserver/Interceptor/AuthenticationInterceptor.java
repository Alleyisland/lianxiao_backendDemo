package com.lianxiao.demo.simpleserver.Interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.lianxiao.demo.simpleserver.exception.AppException;
import com.lianxiao.demo.simpleserver.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class AuthenticationInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");

        if (token==null || token.length()==0){
            throw new AppException("未登录");
        }

        logger.info("认证{}",token);
        Map<String,Claim> verify = TokenUtils.verify(token);
        if (verify==null){
            throw new AppException("用户身份验证失败");
        }
        return true;
    }
}