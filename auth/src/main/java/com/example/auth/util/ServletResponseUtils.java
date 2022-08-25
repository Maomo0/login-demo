package com.example.auth.util;

import com.example.auth.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Mao
 * @creteDateTime: 2022-08-06 14:28
 * @className: ServletResponseUtils
 * @description:
 **/
public class ServletResponseUtils {

    public static void responseError(ServletResponse response, String msg, Integer status){
        HttpServletResponse res = (HttpServletResponse) response;
        res.setStatus(status);
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json;charset=UTF-8");
        try {
            String s = new ObjectMapper().writeValueAsString(new Result(status, msg));
            response.getWriter().append(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
