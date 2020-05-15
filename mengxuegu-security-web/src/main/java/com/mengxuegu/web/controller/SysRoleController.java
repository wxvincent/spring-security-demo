package com.mengxuegu.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengxuegu.web.entities.SysRole;
import com.mengxuegu.web.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import result.MengxueguResult;

@Controller
@RequestMapping("/role")
public class SysRoleController {

    private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    private static final String HTML_PREFIX = "system/role/";

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping(path = {"/", ""})
    public String role() {
        return HTML_PREFIX + "role-list";
    }

    @PreAuthorize("hasAuthority('sys:role:list')")
    @RequestMapping(path = "/page", method = RequestMethod.POST)
    @ResponseBody
    public MengxueguResult page(Page<SysRole> page, SysRole sysRole) {
        return MengxueguResult.ok(sysRoleService.selectPage(page, sysRole));
    }

    @PreAuthorize("hasAnyAuthority('sys:role:add', 'sys:role:edit')")
    @RequestMapping(path = {"/form", "/form/{id}"}, method = RequestMethod.GET)
    public String form(@PathVariable(required = false) Long id, Model model) {
        SysRole sysRole = sysRoleService.findById(id);

        model.addAttribute("role", sysRole);
        return HTML_PREFIX + "role-form";
    }

    @PreAuthorize("hasAnyAuthority('sys:role:add', 'sys:role:edit')")
    @RequestMapping(path = "", method = {RequestMethod.POST, RequestMethod.PUT})
    public String saveOrUpdate(SysRole sysRole) {
        sysRoleService.saveOrUpdate(sysRole);
        return "redirect:/role";
    }

    @PreAuthorize("hasAuthority('sys:role:delete')")
    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public MengxueguResult deleteById(@PathVariable("id") Long id) {
        sysRoleService.deleteById(id);
        return MengxueguResult.ok();
    }
}
