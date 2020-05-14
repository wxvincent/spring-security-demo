package com.mengxuegu.security.properties;

import lombok.Data;

@Data
public class AuthenticationProperties {

    private String loginPage = "/login/page";
    private String loginProcessingUrl = "/login/form";
    private String usernameParameter = "name";
    private String passwordParameter = "pwd";
    private String[] staticPaths = {"/dist/**", "/modules/**", "/plugins/**"};

    private LoginResponseType loginType = LoginResponseType.REDIRECT;

    private String imageCodeUrl = "/code/image";
    private String mobileCodeUrl = "/code/mobile";
    private String mobilePage = "/mobile/page";
    private Integer tokenValidateSeconds = 60 * 60 * 24 * 7;
}
