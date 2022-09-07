package com.example.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.common.CommonVal;
import com.example.auth.common.Result;
import com.example.auth.mapper.UserMapper;
import com.example.auth.po.UserPo;
import com.example.auth.service.LoginService;
import com.example.auth.util.JwtUtil;
import com.example.auth.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-24 16:33
 * @className: UserServiceImpl
 * @description:
 **/
@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, UserPo> implements LoginService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public UserPo getUserByName(String username) {
        QueryWrapper<UserPo> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public Result login(UserVo user) {
        String id = SecurityUtils.getSubject().getSession().getId().toString();
        System.out.println("id = " + id);
        UserPo dbUser = getUserByName(user.getUsername());
        if(Objects.isNull(dbUser) || !Objects.equals(user.getPassword(), dbUser.getPassword())){
            return Result.failure("用户名或密码错误!");
        }
        String token = JwtUtil.sign(user.getUsername(), user.getPassword(), id);
        Map<String, Object> res = new HashMap<>(8);
        res.put("token", token);
        res.put("sid", id);
        res.put("username", user.getUsername());
        redisTemplate.opsForValue().set(user.getUsername()+ ":" + id, token);
        redisTemplate.expire(user.getUsername()+ ":" + id, CommonVal.EXPIRE_TIME_SECOND.longValue(), TimeUnit.SECONDS);
        return Result.success(res);
    }

    @Override
    public Result refreshToken(String token) {
        UserPo user = (UserPo) SecurityUtils.getSubject().getPrincipal();
        if (Objects.isNull(token)){
            return Result.unauthorized("请重新登录！");
        }
        if (Objects.isNull(user)){
            user = getUserByName(JwtUtil.getUsername(token));
        }
        if (JwtUtil.refreshToken(token, user.getUsername(), user.getPassword(), redisTemplate)) {
            return Result.success("刷新token完成!");
        }
        System.out.println("user = " + user);
        return Result.failure("刷新token失败!");
    }

    @Override
    public Result getPermission(String username) {
        List<String> permission = userMapper.getPermission(username);
        Map<String, Object> result = new HashMap<>(4);
        result.put("menuAuth", permission);
        return Result.success(result);
    }

    @Override
    public Result tryLoginInfo() {
        UserPo po = (UserPo) SecurityUtils.getSubject().getPrincipal();
        Map<String, Object> result = new HashMap<>(4);
        List<String> permission = userMapper.getPermission(po.getUsername());
        result.put("menuAuth", permission);
        result.put("username", po.getUsername());
        return Result.success(result);
    }
}
