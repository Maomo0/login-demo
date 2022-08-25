package com.example.auth.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-24 16:28
 * @className: User
 * @description:
 **/
@TableName("user")
public class UserPo implements Serializable {

    @TableId(type = IdType.UUID)
    private String id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;
    @TableField("usertype")
    private String usertype;

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
