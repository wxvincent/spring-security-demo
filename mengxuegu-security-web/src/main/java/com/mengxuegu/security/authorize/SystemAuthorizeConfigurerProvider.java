package com.mengxuegu.security.authorize;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component
public class SystemAuthorizeConfigurerProvider implements AuthorizeConfigurerProvider{

    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers("/user").hasAuthority("sys:user")
                .antMatchers(HttpMethod.GET, "/role").hasAuthority("sys:role")
                .antMatchers(HttpMethod.GET, "/permission").access("hasAuthority('sys:permission') or hasAnyRole('ADMIN')")
        ;
    }
}
