package com.sankuai.avatar.web.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankuai.avatar.web.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局返回值统一处理
 */
@Configuration
public class ResponseBodyHandler {

    @RestControllerAdvice
    static class ResultResponseAdvice implements ResponseBodyAdvice<Object> {
        @Autowired
        private ObjectMapper objectMapper;

        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
            if (body instanceof ApiResponse) {
                return body;
            }
            if (body instanceof String) {
                try {
                    return objectMapper.writeValueAsString(ApiResponse.ofSuccess(body));
                } catch (Exception e) {

                }
            }
            return ApiResponse.ofSuccess(body);
        }
    }
}
