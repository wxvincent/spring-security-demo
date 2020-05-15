package com.mengxuegu.security;

import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("customUserDetailsService")
public class CustomUserDetailsService extends AbstractUserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    //不能删掉，不然报错
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public SysUser findSysUser(String usernameOrMobile) {
        logger.info("请求认证的用户名为：" + usernameOrMobile);
        return sysUserService.findByUsername(usernameOrMobile);
    }

}
