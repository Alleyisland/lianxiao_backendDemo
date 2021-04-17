package com.lianxiao.demo.simpleserver.exception;

import com.lianxiao.demo.simpleserver.util.FastJsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常
     */
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(value = AppException.class)
    @ResponseBody
    public String exceptionHandler(HttpServletRequest req, AppException e){
        logger.error("发生业务异常！原因是：{}",e.getErrorMsg());
        int errCode=Integer.parseInt(e.getErrorCode());
        return FastJsonUtils.resultError(errCode,"internal server error",e.getMessage());
    }

    /**
     * 处理参数错误的异常
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public String exceptionHandler(HttpServletRequest req, MethodArgumentTypeMismatchException e){
        logger.error("请求参数错误！原因是:",e);
        int errCode=Integer.parseInt(ExceptionEnum.BODY_NOT_MATCH.getResultCode());
        return FastJsonUtils.resultError(errCode,"参数类型转换错误",e.getMessage());
    }

    @ExceptionHandler(value = IllegalStateException.class)
    @ResponseBody
    public String exceptionHandler(HttpServletRequest req, IllegalStateException e){
        logger.error("请求参数错误！原因是:",e);
        int errCode=Integer.parseInt(ExceptionEnum.BODY_NOT_MATCH.getResultCode());
        return FastJsonUtils.resultError(errCode,"参数类型错误",e.getMessage());
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String exceptionHandler(HttpServletRequest req, Exception e){
        logger.error("未知异常！原因是:",e);
        int errCode=Integer.parseInt(ExceptionEnum.INTERNAL_SERVER_ERROR.getResultCode());
        return FastJsonUtils.resultError(errCode,"internal server error",ExceptionEnum.INTERNAL_SERVER_ERROR.getResultMsg());
    }
}
