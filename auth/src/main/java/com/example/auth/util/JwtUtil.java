package com.example.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.auth.common.CommonVal;
import com.example.auth.vo.UserVo;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Calendar;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-17 19:56
 * @className: JwtUtil
 * @description:
 **/
public class JwtUtil {
    private static final String SECRET="asdjfoiwerupqeuwr";

    public static boolean verify(String token, String username, String secret){
        // 根据密码生成Jwt校验器
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET + secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            DecodedJWT verify = verifier.verify(token);
            return true;
        } catch (Exception e){
//            e.printStackTrace();
            return false;
        }
    }

    public static String getUsername(String token) {
        try {
            return JWT.decode(token).getClaim("username").asString();
        } catch (JWTDecodeException e) {
            throw new JWTDecodeException("token 解析异常");
        }
    }
    public static String getSid(String token) {
        try {
            return JWT.decode(token).getClaim("sid").asString();
        } catch (JWTDecodeException e) {
            throw new JWTDecodeException("token 解析异常");
        }
    }

    public static String sign(String username, String password, String sessionId){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, CommonVal.EXPIRE_TIME_SECOND);
        return JWT.create()
                .withClaim("username", username).withClaim("sid", sessionId)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(SECRET + password));
    }

    public static boolean refreshToken(String token, String username, String pwd, RedisTemplate<String, Object> template){
        if (verify(token, username, pwd)){
            String sid = getSid(token);
            String tokenKey = username +":"+ sid;
            template.expire(tokenKey, CommonVal.EXPIRE_TIME_SECOND.longValue(), TimeUnit.SECONDS);
            return true;
        }
        return false;
    }
}
