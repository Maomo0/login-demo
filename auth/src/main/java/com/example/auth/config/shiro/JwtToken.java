package com.example.auth.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-17 15:39
 * @className: JwtToken
 * @description:
 **/
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
