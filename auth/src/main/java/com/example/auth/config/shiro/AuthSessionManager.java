package com.example.auth.config.shiro;

import com.example.auth.common.CommonVal;
import com.example.auth.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author: Mao
 * @creteDateTime: 2022-08-07 17:05
 * @className: AuthSessionManager
 * @description:
 **/
public class AuthSessionManager extends DefaultWebSessionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthSessionManager.class);

    @Override
    public Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest servletRequest = WebUtils.toHttp(request);
        String token = servletRequest.getHeader(CommonVal.HEADER_TOKEN);
        String sign = StringUtils.hasLength(servletRequest.getHeader(CommonVal.SIGN))?
                servletRequest.getHeader(CommonVal.SIGN):
                Objects.nonNull(token)? JwtUtil.getSid(token) :servletRequest.getParameter(CommonVal.SIGN);
        if(StringUtils.hasLength(sign)){
            servletRequest.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sign);
            servletRequest.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "sign");
            servletRequest.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sign;
        }
        return super.getSessionId(request, response);
    }

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);

        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }

        if (request != null && null != sessionId) {
            Object sessionObj = request.getAttribute(sessionId.toString());
            if (sessionObj != null) {
                return (Session) sessionObj;
            }
        }

        Session session = super.retrieveSession(sessionKey);
        if (request != null && null != sessionId) {
            request.setAttribute(sessionId.toString(), session);
        }
        return session;
    }

}
