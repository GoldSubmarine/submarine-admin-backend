package com.htnova.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.htnova.common.dto.XPage;
import org.apache.ibatis.annotations.Param;
import com.htnova.modules.system.entity.Permission;
import com.htnova.modules.system.entity.RolePermission;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    XPage<RolePermission> findPage(XPage xPage, @Param("rolePermission") RolePermission rolePermission);

    List<RolePermission> findList(@Param("rolePermission") RolePermission rolePermission);

    List<Permission> getByRoleId(long id);

    List<Permission> getByRoleIds(@Param("ids") List<Long> ids);

}
