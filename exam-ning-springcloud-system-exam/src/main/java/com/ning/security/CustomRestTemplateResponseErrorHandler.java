package com.ning.security;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

public class CustomRestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {
    /**
     * 自定义响应处理
     *
     * @param response
     * @throws IOException
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getRawStatusCode() != 400) {
            super.handleError(response);
        }
    }

}
