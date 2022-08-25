package com.example.auth.common;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.auth.config.shiro.ShiroRealm;
import com.example.auth.util.ServletResponseUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-24 17:20
 * @className: RestExceptionHandler
 * @description:
 **/
@RestControllerAdvice
public class RestExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(JWTVerificationException.class)
    public void tokenException(JWTVerificationException e, ServletResponse response){
        LOGGER.error("JWTVerificationException 异常处理器");
        e.printStackTrace();
        ServletResponseUtils.responseError(response, e.getMessage(), 401);
    }

    @ExceptionHandler(AuthenticationException.class)
    public void shiroException(AuthenticationException e,ServletResponse response){
        LOGGER.error("AuthenticationException 异常处理器");
        e.printStackTrace();
        ServletResponseUtils.responseError(response, e.getMessage(), 401);
//        return new Result(401, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e){
        e.printStackTrace();
        return new Result(500, e.getMessage());
    }
    @ExceptionHandler(JWTDecodeException.class)
    public void decodeException(JWTDecodeException e,ServletResponse response){
        ServletResponseUtils.responseError(response, e.getMessage(), 500);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void validException(MethodArgumentNotValidException e, ServletResponse response){
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String message = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        ServletResponseUtils.responseError(response, message, 500);
    }
}
