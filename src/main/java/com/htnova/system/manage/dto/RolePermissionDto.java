package com.htnova.system.manage.dto;

import com.htnova.common.base.BaseDto;
import com.htnova.system.manage.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class RolePermissionDto extends BaseDto {

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

    private List<Long> permissionList;

}
