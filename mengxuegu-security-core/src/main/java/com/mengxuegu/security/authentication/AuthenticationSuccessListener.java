package com.mengxuegu.security.authentication;

import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthenticationSuccessListener {

    void successListener(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException;
}
