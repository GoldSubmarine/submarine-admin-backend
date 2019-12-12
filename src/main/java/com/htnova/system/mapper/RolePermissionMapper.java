package com.htnova.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.entity.Permission;
import com.htnova.system.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    IPage<RolePermission> findPage(IPage<Void> xPage, @Param("rolePermission") RolePermission rolePermission);

    List<RolePermission> findList(@Param("rolePermission") RolePermission rolePermission);

    List<Permission> getByRoleId(long id);

    List<Permission> getByRoleIds(@Param("ids") List<Long> ids);

}
