package com.mengxuegu.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.mapper.SysPermissionMapper;
import com.mengxuegu.web.service.SysPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Override
    public List<SysPermission> findByUserId(Long userId) {
        if (userId == null) return null;

        List<SysPermission> sysPermissions = baseMapper.selectPermissionByUserId(userId);
        sysPermissions.remove(null);

        return sysPermissions;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        baseMapper.deleteById(id);

        //删除子节点
        LambdaQueryWrapper<SysPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPermission::getParentId, id);
        baseMapper.delete(wrapper);

        return true;
    }
}
