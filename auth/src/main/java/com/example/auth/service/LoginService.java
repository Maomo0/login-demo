package com.example.auth.service;

import com.example.auth.common.Result;
import com.example.auth.po.UserPo;
import com.example.auth.vo.UserVo;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-24 16:32
 * @interfaceName: UserService
 * @description:
 **/
public interface LoginService {

    /**
     * @param name 用户名
     * @return user
     * @date 2022-43-24 16:43
    **/
    UserPo getUserByName(String name);

    /**
     * 处理登录逻辑
     * @author mao
     * @param user 登录用户信息
     * @return result
     * @date 2022-51-30 17:51
    **/
    Result login(UserVo user);

    /**
     * 刷新token
     * @author mao
     * @param token 认证消息
     * @return result
     * @date 2022-46-20 14:46
    **/
    Result refreshToken(String token);

    /**
     * 获取用户权限信息
     * @author mao
     * @param username 用户名
     * @return result
     * @date 2022-49-20 14:49
    **/
    Result getPermission(String username);

    /**
     * 放回用户信息
     * @author mao
     * @return result
     * @date 2022-44-21 14:44
    **/
    Result tryLoginInfo();
}
