package com.example.auth.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-24 17:48
 * @className: LoginVo
 * @description:
 **/
public class LoginVo implements Serializable {
    private String token;
    private String cookieId;
    private Map<String, Object> resource;

    public LoginVo() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    public Map<String, Object> getResource() {
        return resource;
    }

    public void setResource(Map<String, Object> resource) {
        this.resource = resource;
    }
}
