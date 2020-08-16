package com.htnova.system.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.manage.dto.RolePermissionDto;
import com.htnova.system.manage.entity.Permission;
import com.htnova.system.manage.entity.RolePermission;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    IPage<RolePermission> findPage(IPage<Void> xPage, @Param("rolePermissionDto") RolePermissionDto rolePermissionDto);

    List<RolePermission> findList(@Param("rolePermissionDto") RolePermissionDto rolePermissionDto);

    List<Permission> getByRoleId(long id);

    List<Permission> getByRoleIds(@Param("ids") List<Long> ids);
}
