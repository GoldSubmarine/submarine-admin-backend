package com.htnova.system.dto;

import com.htnova.common.base.BaseDto;
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
