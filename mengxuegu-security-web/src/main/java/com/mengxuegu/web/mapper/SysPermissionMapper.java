package com.mengxuegu.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mengxuegu.web.entities.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<SysPermission> selectPermissionByUserId(@Param("userId") Long userId);

    List<SysPermission> selectPermissionByRoleId(@Param("roleId") Long roleId);
}
