package com.ning.security;

import com.alibaba.fastjson.JSON;
import com.ning.model.Result;
import com.ning.utils.ServletUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AccessDeniedHandler 该类用来统一处理 AccessDeniedException 异常
 */
public class CustomAccessDeniedHandler extends OAuth2AccessDeniedHandler {

    /**
     * 处理 AccessDeniedException 异常
     * 自定义处理访问被拒绝异常，向客户端返回适当的错误信息
     * @param request
     * @param response
     * @param authException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) {
        String msg = authException.getMessage();
        Result result = Result.fail(HttpStatus.FORBIDDEN.value(), msg);
        result.setData("");
        ServletUtils.renderString(response, JSON.toJSONString(result));
    }

}
