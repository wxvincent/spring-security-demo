package com.mengxuegu.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.web.entities.SysUser;

public interface SysUserService extends IService<SysUser> {

    SysUser findByUsername(String username);

    SysUser findByMobile(String mobile);

    IPage<SysUser> selectPage(Page<SysUser> page, SysUser sysUser);

    SysUser findById(Long id);

    boolean deleteById(Long id);
}
