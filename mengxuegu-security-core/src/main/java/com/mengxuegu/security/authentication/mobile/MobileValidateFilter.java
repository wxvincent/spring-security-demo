package com.mengxuegu.security.authentication.mobile;

import com.mengxuegu.security.authentication.CustomAuthenticationFailureHandler;
import com.mengxuegu.security.authentication.exception.ValidateCodeException;
import com.mengxuegu.security.controller.CustomMobileController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MobileValidateFilter extends OncePerRequestFilter {

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("/mobile/form".equals(request.getRequestURI()) &&
        "post".equalsIgnoreCase(request.getMethod())) {
            try {
                validate(request);
            } catch (AuthenticationException e) {
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) {
        String sessionCode = (String) request.getSession().getAttribute(CustomMobileController.SESSION_KEY);

        String inputCode = request.getParameter("code");

        if (StringUtils.isBlank(inputCode)) {
            throw new ValidateCodeException("验证码不能为空！");
        }

        if (!inputCode.equalsIgnoreCase(sessionCode)) {
            throw new ValidateCodeException("验证码输入错误！");
        }
    }
}
