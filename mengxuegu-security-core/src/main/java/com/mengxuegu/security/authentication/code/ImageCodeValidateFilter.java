package com.mengxuegu.security.authentication.code;

import com.mengxuegu.security.authentication.exception.ValidateCodeException;
import com.mengxuegu.security.controller.CustomLoginController;
import com.mengxuegu.security.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("imageCodeValidateFilter")
public class ImageCodeValidateFilter extends OncePerRequestFilter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (securityProperties.getAuthentication().getLoginProcessingUrl()
        .equals(request.getRequestURI()) && request.getMethod().equalsIgnoreCase("post")) {
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
        String sessionCode = (String) request.getSession().getAttribute(CustomLoginController.SESSION_KEY);

        String inputCode = request.getParameter("code");

        if (StringUtils.isBlank(inputCode)) {
            throw new ValidateCodeException("验证码不能为空！");
        }

        if (!inputCode.equalsIgnoreCase(sessionCode)) {
            throw new ValidateCodeException("验证码输入错误！");
        }
    }
}
