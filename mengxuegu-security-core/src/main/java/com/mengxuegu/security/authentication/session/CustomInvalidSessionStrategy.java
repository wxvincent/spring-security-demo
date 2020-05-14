package com.mengxuegu.security.authentication.session;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.InvalidSessionStrategy;
import result.MengxueguResult;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {

    private SessionRegistry sessionRegistry;

    public CustomInvalidSessionStrategy(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        sessionRegistry.removeSessionInformation(request.getRequestedSessionId());

        cancleCookie(request, response);
        MengxueguResult mengxueguResult = MengxueguResult.build(
                HttpStatus.UNAUTHORIZED.value(), "登录已超时，请重新登录！");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(mengxueguResult.toJsonString());
    }

    private void cancleCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
        response.addCookie(cookie);
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }
}
