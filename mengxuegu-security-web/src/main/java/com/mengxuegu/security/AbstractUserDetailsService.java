package com.mengxuegu.security;

import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.service.SysPermissionService;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public abstract class AbstractUserDetailsService implements UserDetailsService {

    @Autowired
    private SysPermissionService sysPermissionService;

    //该方法交由子类实现
    public abstract SysUser findSysUser(String usernameOrMobile);

    @Override
    public UserDetails loadUserByUsername(String usernameOrMobile) throws UsernameNotFoundException {
        SysUser sysUser = findSysUser(usernameOrMobile);
        //通过用户id查询权限信息
        findSysPermission(sysUser);
        return sysUser;
    }

    private void findSysPermission(SysUser sysUser) {
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        //查询用户所拥有的权限
        List<SysPermission> sysPermissions = sysPermissionService.findByUserId(sysUser.getId());
        if (CollectionUtils.isEmpty(sysPermissions)) return;

        //前端页面渲染时会用到
        sysUser.setPermissions(sysPermissions);

        //封装用户信息和权限信息
        List<GrantedAuthority> list = Lists.newArrayList();
        for (SysPermission sysPermission : sysPermissions) {
            list.add(new SimpleGrantedAuthority(sysPermission.getCode()));
        }
        sysUser.setAuthorities(list);
    }
}
