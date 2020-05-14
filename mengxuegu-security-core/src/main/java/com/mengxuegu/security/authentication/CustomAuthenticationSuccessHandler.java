package com.mengxuegu.security.authentication;

import com.alibaba.fastjson.JSON;
import com.mengxuegu.security.properties.LoginResponseType;
import com.mengxuegu.security.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import result.MengxueguResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("customAuthenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private AuthenticationSuccessListener authenticationSuccessListener;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //认证成功后，加载当前用户所拥有的权限资源
        if (authenticationSuccessListener != null) {
            authenticationSuccessListener.successListener(request, response, authentication);
        }

        if (LoginResponseType.JSON.equals(
                securityProperties.getAuthentication().getLoginType()
        )) {
            MengxueguResult result = MengxueguResult.ok("认证成功");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(result.toJsonString());
        } else {
            logger.info("authentication: " + JSON.toJSONString(authentication));
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
