package com.ning.security;

import com.alibaba.fastjson.JSON;
import com.ning.constant.CommonConstants;
import com.ning.model.Result;
import com.ning.utils.ServletUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

    /**
     * 自定义返回结果
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result = Result.fail(HttpStatus.UNAUTHORIZED.value(), CommonConstants.BAD_TOKEN);
        result.setData(authException.getMessage());
        ServletUtils.renderString(response, JSON.toJSONString(result));
    }
}
