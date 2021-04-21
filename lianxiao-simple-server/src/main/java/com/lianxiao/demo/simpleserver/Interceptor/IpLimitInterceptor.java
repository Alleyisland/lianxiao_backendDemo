package com.lianxiao.demo.simpleserver.Interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.lianxiao.demo.simpleserver.exception.AppException;
import com.lianxiao.demo.simpleserver.service.IpLimitService;
import com.lianxiao.demo.simpleserver.utils.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IpLimitInterceptor implements HandlerInterceptor {
    @Autowired
    private IpLimitService ipLimitService;
    private Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ip=parseIpAddress(request);
        if (ip==null||"unknown".equalsIgnoreCase(ip)){
            throw new AppException("请求无ip?");
        }
        logger.info("ip{}: 请求{}",ip,request.getPathInfo());
        boolean permit=ipLimitService.validate(ip);
        if (!permit){
            throw new AppException("访问过于频繁,请稍后再试");
        }
        return true;
    }

    private String parseIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null) {
            //对于通过多个代理的情况，最后IP为客户端真实IP,多个IP按照','分割
            int position = ip.indexOf(",");
            if (position > 0) {
                ip = ip.substring(0, position);
            }
        }
        return ip;
    }
}
