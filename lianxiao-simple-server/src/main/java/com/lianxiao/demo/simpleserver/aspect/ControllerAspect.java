package com.lianxiao.demo.simpleserver.aspect;

import com.alibaba.fastjson.JSON;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 类描述: 日志拦截器，打印controller层的入参和出参<br>
 *
 */
@Aspect
@Component
public class ControllerAspect {

    private final Logger logger = LogManager.getLogger(ControllerAspect.class);

    /**
     * Controller aspect.
     */
    @Pointcut("execution(* com.lianxiao.demo.simpleserver.service.*.*(..))")
    public void controllerAspect() {
    }

    /**
     * Around 手动控制调用核心业务逻辑，以及调用前和调用后的处理,
     * <p>
     * 注意：当核心业务抛异常后，立即退出，转向AfterAdvice 执行完AfterAdvice，再转到ThrowingAdvice
     *
     * @param pjp the pjp
     * @return object
     * @throws Throwable the throwable
     */
    @Around(value = "controllerAspect()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        //防止不是http请求的方法，例如：scheduled
        if (ra == null) {
            return pjp.proceed();
        }
        HttpServletRequest request = sra.getRequest();

        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        // 1.类.方法全限定名
        logger.info("CLASS_METHOD : " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        // 2.入参打印
        logger.info("REQUEST ARGS : " + JSON.toJSONString(pjp.getArgs()));

        long startTime = System.currentTimeMillis();
        try {
            Object response = pjp.proceed();
            // 3.出参打印
            logger.info("RESPONSE:{}", response != null ? JSON.toJSONString(response) : "");
            return response;
        } catch (Throwable e) {
            if (e instanceof MethodArgumentNotValidException) {
                logger.info("RESPONSE ERROR:{}", e.getMessage());
            } else {
                logger.error("RESPONSE ERROR:{}", Arrays.toString(e.getStackTrace()));
            }
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            logger.info("SPEND TIME : {}ms", (endTime - startTime));
        }
    }
}