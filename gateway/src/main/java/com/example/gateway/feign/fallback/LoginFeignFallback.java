package com.example.gateway.feign.fallback;

import com.example.gateway.common.Result;
import com.example.gateway.feign.LoginFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-31 17:26
 * @className: LoginFeignFallback
 * @description:
 **/
@Component
public class LoginFeignFallback implements LoginFeign {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginFeignFallback.class);

    @Override
    public Result refreshToken(String token) {
        LOGGER.error("login 服务调度出错");
        return Result.failure("远程服务报错！");
    }
}
