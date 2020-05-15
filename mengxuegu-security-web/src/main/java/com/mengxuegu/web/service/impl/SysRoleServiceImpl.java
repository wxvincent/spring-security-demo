package com.mengxuegu.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.entities.SysRole;
import com.mengxuegu.web.mapper.SysPermissionMapper;
import com.mengxuegu.web.mapper.SysRoleMapper;
import com.mengxuegu.web.service.SysRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public IPage<SysRole> selectPage(Page<SysRole> page, SysRole sysRole) {
        return baseMapper.selectPage(page, sysRole);
    }

    @Override
    public SysRole findById(Long id) {
        if (id == null) return new SysRole();

        SysRole sysRole = baseMapper.selectById(id);

        List<SysPermission> sysPermissions = sysPermissionMapper.selectPermissionByRoleId(sysRole.getId());
        sysRole.setPerList(sysPermissions);
        return sysRole;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        baseMapper.deleteById(id);

        baseMapper.deleteRolePermissionByRoleId(id);

        return true;
    }

    @Transactional
    @Override
    public boolean saveOrUpdate(SysRole sysRole) {
        sysRole.setUpdateDate(new Date());
        boolean flag = super.saveOrUpdate(sysRole);

        if (flag) {
            baseMapper.deleteRolePermissionByRoleId(sysRole.getId());

            if (CollectionUtils.isNotEmpty(sysRole.getPerIds())) {
                baseMapper.saveRolePermission(sysRole.getId(), sysRole.getPerIds());
            }
        }
        return flag;
    }
}
