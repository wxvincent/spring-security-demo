package com.mengxuegu;

import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.entities.SysRole;
import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.service.SysPermissionService;
import com.mengxuegu.web.service.SysRoleService;
import com.mengxuegu.web.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = WebApplication.class)
public class TestWebApplication {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Test
    public void test1() {
        SysUser meng = sysUserService.findByUsername("admin");
        System.out.println(meng);

        List<SysUser> list = sysUserService.list();
        list.forEach(System.out::println);
    }

    @Test
    public void test2() {
        SysRole sysRole = sysRoleService.getById(9);
        System.out.println(sysRole);
    }

    @Test
    public void test3() {
        List<SysPermission> list = sysPermissionService.findByUserId(9L);
        System.out.println("permissions: " + list);
    }
}
