package org.javahub.submarine.modules.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.base.BaseEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission extends BaseEntity {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long permissionId;
}
