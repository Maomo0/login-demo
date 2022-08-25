package com.example.auth.common;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-17 16:00
 * @interfaceName: CommonVal
 * @description:
 **/
public interface CommonVal {
    Integer EXPIRE_TIME_SECOND = 6000;

    String AUTH_SESSION_PREFIX = "access_token:";
    String AUTH_CACHE_PREFIX = "shiroCache:";
    String AUTH_COOKIE = "user_token";
    String HEADER_TOKEN = "Authorization";
    String SIGN = "Sign";
}
