package org.javahub.submarine.modules.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.javahub.submarine.common.base.BaseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class RolePermission extends BaseEntity {

    /**
     * 权限id
     */
    private Long permissionId;

    /**
     * 角色id
     */
    private Long roleId;

}
