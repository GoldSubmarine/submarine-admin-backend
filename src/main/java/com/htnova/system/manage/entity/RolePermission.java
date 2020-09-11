package com.htnova.system.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.htnova.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@TableName("t_sys_role_permission")
public class RolePermission extends BaseEntity {
    /** 权限id */
    private Long permissionId;

    /** 角色id */
    private Long roleId;
}
