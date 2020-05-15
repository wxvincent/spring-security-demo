package com.mengxuegu.web.controller;

import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.service.SysPermissionService;
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

@Controller
@RequestMapping("/permission")
public class SysPermissionController {

    private static final Logger logger = LoggerFactory.getLogger(SysPermissionController.class);

    private static final String HTML_PREFIX = "system/permission/";

    @Autowired
    private SysPermissionService sysPermissionService;

    @PreAuthorize("hasAuthority('sys:permission')")
    @RequestMapping(path = {"/", ""})
    public String permission() {
        return HTML_PREFIX + "permission-list";
    }

    @PreAuthorize("hasAuthority('sys:permission:list')")
    @RequestMapping("/list")
    @ResponseBody
    public MengxueguResult list() {
        return MengxueguResult.ok(sysPermissionService.list());
    }

    @PreAuthorize("hasAnyAuthority('sys:permission:add', 'sys:permission:edit')")
    @RequestMapping(path = {"/form", "/form/{id}"}, method = RequestMethod.GET)
    public String form(@PathVariable(required = false) Long id, Model model) {
        SysPermission sysPermission = sysPermissionService.getById(id);

        model.addAttribute("permission", sysPermission == null ? new SysPermission() : sysPermission);
        return HTML_PREFIX + "permission-form";
    }

    @PreAuthorize("hasAnyAuthority('sys:permission:add', 'sys:permission:edit')")
    @RequestMapping(path = "", method = {RequestMethod.POST, RequestMethod.PUT})
    public String saveOrUpdate(SysPermission sysPermission) {
        sysPermissionService.saveOrUpdate(sysPermission);
        return "redirect:/permisssion";
    }

    @PreAuthorize("hasAuthority('sys:permission:delete')")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public MengxueguResult deleteById(@PathVariable("id") Long id) {
        sysPermissionService.deleteById(id);
        return MengxueguResult.ok();
    }
}
