package com.mengxuegu.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mengxuegu.web.entities.SysPermission;

import java.util.List;

public interface SysPermissionService extends IService<SysPermission> {

    List<SysPermission> findByUserId(Long userId);

    boolean deleteById(Long id);
}
