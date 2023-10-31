package com.ning.handler;

import com.ning.constant.CommonConstants;
import com.ning.exception.BaseException;
import com.ning.model.Result;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    public Result handle(BaseException e) {
        return Result.fail("普通异常", e.getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = InvalidGrantException.class)
    public Result handle(InvalidGrantException e) {
        String msg = e.getMessage();
        if (CommonConstants.BAD_CREDENTIALS.equals(msg)) {
            msg = CommonConstants.BAD_PASSWORD;
        }
        return Result.fail("身份验证授权异常", msg);
    }

}
