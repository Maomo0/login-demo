package com.example.auth.config.shiro;


import com.auth0.jwt.exceptions.*;
import com.example.auth.common.CommonVal;
import com.example.auth.po.UserPo;
import com.example.auth.service.LoginService;
import com.example.auth.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-17 17:17
 * @className: ShiroRealm
 * @description:
 **/
public class ShiroRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroRealm.class);

    @Resource
    private LoginService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public boolean supports(AuthenticationToken token){return token instanceof JwtToken;}

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进入授权");
        UserPo user = (UserPo) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuth = new SimpleAuthorizationInfo();
        simpleAuth.addStringPermission("/hello");
        simpleAuth.addStringPermission("/update");
        return simpleAuth;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 验证token 是否有效
        try {
            System.out.println("身份认证");
            String token = (String) authenticationToken.getCredentials();
            String username = JwtUtil.getUsername(token);
            String sign = JwtUtil.getSid(token);
            UserPo user = userService.getUserByName(username);
            if(Objects.isNull(user)){
                throw new IncorrectCredentialsException("用户名或密码错误!");
            }
            String redisKey = username +":"+ sign;
            String redisToken = (String) redisTemplate.opsForValue().get(redisKey);
            System.out.println("token = " + redisToken);
            if(Objects.isNull(redisToken)){
                throw new AuthenticationException("身份验证已过期请重新登录!");
            }
            if (JwtUtil.verify(redisToken, username, user.getPassword())){
                // 保证用户操作不掉线 token取redis中的值
                String newToken = JwtUtil.sign(username, user.getPassword(), sign);
                redisTemplate.opsForValue().set(redisKey, newToken);
                redisTemplate.expire(redisKey, CommonVal.EXPIRE_TIME_SECOND.longValue(), TimeUnit.SECONDS);
                return new SimpleAuthenticationInfo(user, token, getName());
            }
        } catch (SignatureVerificationException | InvalidClaimException e) {
            LOGGER.error("无效token");
            e.printStackTrace();
            throw new JWTVerificationException("无效token!");
        } catch (TokenExpiredException e){
            LOGGER.error("身份验证已过期请重新登录!");
            e.printStackTrace();
            throw new JWTVerificationException("身份验证已过期请重新登录!");
        } catch (JWTDecodeException e){
            LOGGER.error("token解析异常!");
            e.printStackTrace();
            throw new JWTDecodeException("token解析异常!");
        } catch (AuthenticationException e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new AuthenticationException(e.getMessage());
        } catch (Exception e){
            LOGGER.error("身份验证失败,请稍后重试!");
            e.printStackTrace();
            throw new AuthenticationException("身份验证失败,请稍后重试!");
        }  finally {
            Serializable id = SecurityUtils.getSubject().getSession().getId();
            redisTemplate.delete(CommonVal.AUTH_SESSION_PREFIX + id);
        }
        return null;
    }

}
