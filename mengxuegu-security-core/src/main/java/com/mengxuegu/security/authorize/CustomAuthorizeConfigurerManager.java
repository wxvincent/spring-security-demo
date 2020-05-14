package com.mengxuegu.security.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthorizeConfigurerManager implements AuthorizeConfigurerManager{

    @Autowired
    private List<AuthorizeConfigurerProvider> authorizeConfigurerProviders;

    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for (AuthorizeConfigurerProvider provider : authorizeConfigurerProviders) {
            provider.configure(config);
        }

        config.anyRequest().authenticated();
    }
}
