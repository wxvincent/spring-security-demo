package com.mengxuegu.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengxuegu.web.entities.SysRole;
import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.service.SysRoleService;
import com.mengxuegu.web.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import result.MengxueguResult;

import java.util.List;

@Controller
@RequestMapping("/user")
public class SysUserController {

    private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    private static final String HTML_PREFIX = "system/user/";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @PreAuthorize("hasAuthority('sys:user')")
    @RequestMapping(path = {"/", ""})
    public String user() {
        return HTML_PREFIX + "user-list";
    }

    @PreAuthorize("hasAnyAuthority('sys:user:add', 'sys:user:edit')")
    @RequestMapping(path = {"/form", "/form/{id}"}, method = RequestMethod.GET)
    public String form(@PathVariable(required = false) Long id, Model model) {
        List<SysRole> roleList = sysRoleService.list();
        model.addAttribute("roleList", roleList);

        SysUser user = sysUserService.findById(id);
        model.addAttribute("user", user);
        return HTML_PREFIX + "user-form";
    }

    @PreAuthorize("hasAuthority('sys:user:list')")
    @RequestMapping(path = "/page", method = RequestMethod.POST)
    @ResponseBody
    public MengxueguResult page(Page<SysUser> page, SysUser sysUser) {
        return MengxueguResult.ok(sysUserService.selectPage(page, sysUser));
    }

    @PreAuthorize("hasAnyAuthority('sys:user:add', 'sys:user:edit')")
    @RequestMapping(path = "", method = {RequestMethod.POST, RequestMethod.PUT})
    public String saveOrUpdate(SysUser sysUser) {
        sysUserService.saveOrUpdate(sysUser);
        return "redirect:/user";
    }

    @PreAuthorize("hasAuthority('sys:user:delete')")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public MengxueguResult deleteById(@PathVariable("id") Long userId) {
        sysUserService.deleteById(userId);
        return MengxueguResult.ok();
    }
}
