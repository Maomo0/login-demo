package com.example.gateway.feign;

import com.example.gateway.common.Result;
import com.example.gateway.feign.fallback.LoginFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-31 17:08
 * @className: LoginFeign
 * @description:
 **/
@FeignClient(value = "auth", url = "http://localhost:8000", fallback = LoginFeignFallback.class)
public interface LoginFeign {

    @GetMapping("/refresh")
    Result refreshToken(@RequestParam("token") String token);
}
