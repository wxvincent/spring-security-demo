package com.mengxuegu.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengxuegu.web.entities.SysRole;
import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.mapper.SysRoleMapper;
import com.mengxuegu.web.mapper.SysUserMapper;
import com.mengxuegu.web.service.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    //加密后的1234
    private static final String PASSWORD_DEFAULT = "$2a$10$rDkPvvAFV8kqwvKJzwlRv.i.q.wz1w1pz0SFsHn/55jNeZFQv/eCm";

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public SysUser findByUsername(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public SysUser findByMobile(String mobile) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> page, SysUser sysUser) {
        return baseMapper.selectPage(page, sysUser);
    }

    @Override
    public SysUser findById(Long id) {
        if (id == null) return new SysUser();

        SysUser sysUser = baseMapper.selectById(id);

        List<SysRole> sysRoleList = sysRoleMapper.findByUserId(id);
        sysUser.setRoleList(sysRoleList);

        return sysUser;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        SysUser sysUser = baseMapper.selectById(id);

        sysUser.setUpdateDate(new Date());
        sysUser.setEnabled(false);
        baseMapper.updateById(sysUser);

        return true;
    }

    @Transactional
    @Override
    public boolean saveOrUpdate(SysUser sysUser) {
        if (sysUser.getId() == null) sysUser.setPassword(PASSWORD_DEFAULT);
        sysUser.setUpdateDate(new Date());

        boolean flag = super.saveOrUpdate(sysUser);
        if (flag) {
            //先删再新建
            baseMapper.deleteUserRoleByUserId(sysUser.getId());
            if (CollectionUtils.isNotEmpty(sysUser.getRoleIds())) {
                baseMapper.saveUserRole(sysUser.getId(), sysUser.getRoleIds());
            }
        }

        return flag;
    }
}
