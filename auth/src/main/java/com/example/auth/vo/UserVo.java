package com.example.auth.vo;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-17 17:26
 * @className: User
 * @description:
 **/
public class UserVo implements Serializable {
    private String id;
    @NotBlank(message = "用户名不能为空！")
    private String username;
    @NotBlank(message = "密码不能为空！")
    private String password;

    public UserVo() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
