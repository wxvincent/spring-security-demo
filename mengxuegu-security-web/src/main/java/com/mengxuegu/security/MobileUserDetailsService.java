package com.mengxuegu.security;

import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("mobileUserDetailsService")
public class MobileUserDetailsService extends AbstractUserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(MobileUserDetailsService.class);

    @Autowired
    private SysUserService sysUserService;

    @Override
    public SysUser findSysUser(String usernameOrMobile) {
        logger.info("请求的手机号为：" + usernameOrMobile);
        return sysUserService.findByMobile(usernameOrMobile);
    }

}
