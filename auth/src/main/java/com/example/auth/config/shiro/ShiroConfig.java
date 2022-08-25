package com.example.auth.config.shiro;

import com.example.auth.common.CommonVal;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-17 15:41
 * @className: ShiroConfig
 * @description:
 **/
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(defaultWebSecurityManager());
        factoryBean.setLoginUrl("/login");

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>(3);
        Map<String, Filter> filterMap = new LinkedHashMap<>(1);

        filterMap.put("jwt", new JwtFilter());
        factoryBean.setFilters(filterMap);
        // 登录请求不会执行过滤器 linkedHashMap中anon顺序先于自定义过滤器
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/refresh", "anon");
        filterChainDefinitionMap.put("/**", "jwt");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(shiroRealm());
        manager.setSessionManager(defaultWebSessionManager());
        manager.setCacheManager(redisCacheManager());
        return manager;
    }

    @Bean
    public DefaultWebSessionManager defaultWebSessionManager(){
        DefaultWebSessionManager manager = new AuthSessionManager();
        manager.setSessionIdCookie(simpleCookie());
        manager.setSessionDAO(redisSessionDAO());
        manager.setCacheManager(redisCacheManager());

        manager.setSessionIdCookieEnabled(true);
        manager.setDeleteInvalidSessions(true);
        manager.setSessionIdUrlRewritingEnabled(false);
        return manager;
    }
    @Bean
    public SimpleCookie simpleCookie(){
        SimpleCookie simpleCookie = new SimpleCookie(CommonVal.SIGN);
        simpleCookie.setPath("/");
        simpleCookie.setMaxAge(CommonVal.EXPIRE_TIME_SECOND);
        simpleCookie.setHttpOnly(true);
        return simpleCookie;
    }

    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setDatabase(1);
        return redisManager;
    }

    @Bean
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO  sessionDAO = new RedisSessionDAO();
        sessionDAO.setExpire(CommonVal.EXPIRE_TIME_SECOND);
        sessionDAO.setRedisManager(redisManager());
        sessionDAO.setKeyPrefix(CommonVal.AUTH_SESSION_PREFIX);
        return sessionDAO;
    }

    public RedisCacheManager redisCacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        cacheManager.setExpire(CommonVal.EXPIRE_TIME_SECOND);
        cacheManager.setKeyPrefix(CommonVal.AUTH_CACHE_PREFIX);
        cacheManager.setPrincipalIdFieldName("id");
        return cacheManager;
    }

    @Bean
    public ShiroRealm shiroRealm(){
        return new ShiroRealm();
    }

//    public RedisCacheManager redisCacheManager(){
//        RedisCacheManager cacheManager = new RedisCacheManager();
//        cacheManager.setRedisManager(redisManager());
//        cacheManager.setExpire(CommonVal.EXPIRE_TIME_SECOND);
//        cacheManager.setKeyPrefix(CommonVal.AUTH_CACHE_PREFIX);
//    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }


}
