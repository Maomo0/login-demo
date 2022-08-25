package com.example.auth.common;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author: Mao
 * @creteDateTime: 2022-07-24 17:29
 * @className: Result
 * @description:
 **/
public class Result implements Serializable {
    private Integer code;
    private Object data;
    private String message;

    public Result(){}
    public Result(Object data){
        this.code = HttpStatus.OK.value();
        this.data = data;
        this.message = "请求成功";
    }
    public Result(String message){
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.message = message;
    }
    public Result(Integer code, Object data){
        this.data = data;
        this.code = code;
    }
    public Result(Integer code, String message){
        this.code = code;
        this.message = message;
    }
    public static Result success(Object data){
        return new Result(data);
    }
    public static Result failure(String msg){
        return new Result(msg);
    }
    public static Result unauthorized(String msg){return new Result(401, msg);}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
