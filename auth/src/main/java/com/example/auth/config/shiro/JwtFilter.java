package com.example.auth.config.shiro;

import com.example.auth.common.CommonVal;
import com.example.auth.common.Result;
import com.example.auth.util.JwtUtil;
import com.example.auth.util.ServletResponseUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.tomcat.util.http.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class JwtFilter extends BasicHttpAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);


    public JwtFilter(){}

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            System.out.println("执行isAccessAllowed");
            return executeLogin(request, response);
        } catch (Exception e) {
            ServletResponseUtils.responseError(response, e.getMessage(), 401);
           return false;
        }
    }

//    @Override
//    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
//        System.out.println("执行isLoginAttempt");
//        HttpServletRequest servletRequest = (HttpServletRequest) request;
//        String token = servletRequest.getHeader(CommonVal.HEADER_TOKEN);
//        return token != null;
//    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String remoteAddr = req.getRemoteAddr();
        LOGGER.info("remote address" + remoteAddr);
        System.out.println("执行executeLogin");
        String token = req.getHeader(CommonVal.HEADER_TOKEN);
        String sign = req.getHeader(CommonVal.SIGN);
        if (Objects.isNull(token)){
            throw new AuthenticationException("请重新登录！");
        }
        JwtToken jwtToken = new JwtToken(token);
        getSubject(request, response).login(jwtToken);
        return true;
    }

    /**
     * 过滤器的前置处理,对跨域提供支持
     * @author mao
     * @param request 请求体
     * @param response 响应体
     * @return boolean
     * @date 2022-18-30 12:18
    **/
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        servletResponse.setHeader("Access-Control-Allow-Credentials", "true");
//        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
        servletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        servletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization,sessionToken,X-TOKEN");
        if(servletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            servletResponse.setStatus(HttpStatus.OK.value());
        }
        return super.preHandle(request, response);
    }

}