package org.javahub.submarine.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.Permission;
import org.javahub.submarine.modules.system.entity.RolePermission;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    XPage<RolePermission> findPage(XPage xPage, @Param("rolePermission") RolePermission rolePermission);

    List<RolePermission> findList(@Param("rolePermission") RolePermission rolePermission);

    List<Permission> getByRoleId(long id);

    List<Permission> getByRoleIds(@Param("ids") List<Long> ids);

}
