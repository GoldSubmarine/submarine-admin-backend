package com.htnova.system.dto;

import com.htnova.common.base.BaseEntity;
import com.htnova.system.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RolePermissionDto extends BaseEntity {

    /**
     * 权限id
     */
    private Long permissionId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 类型
     */
    private Permission.PermissionType type;

    List<Long> permissionList;

}
