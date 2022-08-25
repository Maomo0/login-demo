package com.example.auth.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author: Mao
 * @creteDateTime: 2022-08-20 14:17
 * @className: ResponeResultWraper
 * @description:
 **/
@RestControllerAdvice(basePackages = "com.example.auth.controller")
public class ResponseResultDecorator implements ResponseBodyAdvice<Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseResultDecorator.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().isAssignableFrom(Result.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return new Result(body);
    }
}
