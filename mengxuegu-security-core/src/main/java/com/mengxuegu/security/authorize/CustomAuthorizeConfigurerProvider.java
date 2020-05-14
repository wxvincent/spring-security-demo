package com.mengxuegu.security.authorize;

import com.mengxuegu.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthorizeConfigurerProvider implements AuthorizeConfigurerProvider{

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(securityProperties.getAuthentication().getLoginPage(),
                securityProperties.getAuthentication().getImageCodeUrl(),
                securityProperties.getAuthentication().getMobilePage(),
                securityProperties.getAuthentication().getMobileCodeUrl()
        ).permitAll();
    }
}
