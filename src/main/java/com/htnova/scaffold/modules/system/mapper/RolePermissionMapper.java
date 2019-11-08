package com.htnova.scaffold.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.htnova.scaffold.common.dto.XPage;
import com.htnova.scaffold.modules.system.entity.Permission;
import com.htnova.scaffold.modules.system.entity.RolePermission;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    XPage<RolePermission> findPage(XPage xPage, @Param("rolePermission") RolePermission rolePermission);

    List<RolePermission> findList(@Param("rolePermission") RolePermission rolePermission);

    List<Permission> getByRoleId(long id);

    List<Permission> getByRoleIds(@Param("ids") List<Long> ids);

}
