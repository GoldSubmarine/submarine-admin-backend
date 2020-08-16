package com.htnova.system.manage.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.htnova.common.base.BaseDto;
import com.htnova.system.manage.entity.Permission;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class RolePermissionDto extends BaseDto {
    /** 权限id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long permissionId;

    /** 角色id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    /** 类型 */
    private Permission.PermissionType type;

    private List<Long> permissionList;
}
