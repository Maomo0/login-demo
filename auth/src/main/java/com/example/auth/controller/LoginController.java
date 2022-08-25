package com.example.auth.controller;

import com.example.auth.common.CommonVal;
import com.example.auth.common.Result;
import com.example.auth.service.LoginService;
import com.example.auth.util.JwtUtil;
import com.example.auth.util.ServletResponseUtils;
import com.example.auth.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.tomcat.util.http.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-23 14:44
 * @className: LoginController
 * @description:
 **/
@RestController
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private LoginService service;

    @PostMapping(value = "/login")
    public Result login(@RequestBody @Validated UserVo user, HttpServletRequest request){
        String token = request.getHeader(CommonVal.HEADER_TOKEN);
        if(Objects.nonNull(token)){
            String key = JwtUtil.getUsername(token) + ":" + JwtUtil.getSid(token);
            redisTemplate.delete(key);
        }
        SecurityUtils.getSubject().logout();
        return service.login(user);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UserVo userVo){
        return Result.success("update");
    }

    @GetMapping("/refresh")
    public Result refreshToken(String token){
        System.out.println("refreshToken token = " + token);
        return service.refreshToken(token);
    }

    @GetMapping("/isPermitted")
    public Result permitted(@RequestParam("path") String path, @RequestParam("method") String httpMethod, @RequestParam("token") String token){
        boolean permitted = SecurityUtils.getSubject().isPermitted(path);
        if(!permitted){
            return new Result(401, "当前地址无访问权限！");
        }
        return Result.success(true);
    }
    @GetMapping("/logout")
    public Result logoutAuth(HttpServletRequest request){
        String token = request.getHeader(CommonVal.HEADER_TOKEN);
        if(Objects.isNull(token)){
            return Result.failure("退出登录失败, 请稍后重试！");
        }
        String sid = JwtUtil.getSid(token);
        String username = JwtUtil.getUsername(token);
        LOGGER.info("用户:" + username + "退出！");
        redisTemplate.delete(username + ":" + sid);
        redisTemplate.delete(CommonVal.AUTH_SESSION_PREFIX + sid);
        SecurityUtils.getSubject().logout();
        return Result.success("操作完成！");
    }
    @GetMapping("/permission/{username}")
    public Result getPermission(@PathVariable("username") String username, HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader(CommonVal.HEADER_TOKEN);
        if(Objects.isNull(token)){
            ServletResponseUtils.responseError(response, "请先登录，在进行此操作！", 401);
            return Result.unauthorized("请先登录，在进行此操作！");
        }
        if(Objects.isNull(username)){
            username = JwtUtil.getUsername(token);
        }
        return service.getPermission(username);
    }

    @GetMapping("/tryLoginInfo")
    public Result tryLoginInfo(@RequestHeader(CommonVal.HEADER_TOKEN) String token){
        if (Objects.isNull(token)){
            return Result.unauthorized("请重新登录！");
        }
        return service.tryLoginInfo();
    }
}
