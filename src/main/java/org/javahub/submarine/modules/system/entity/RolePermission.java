package org.javahub.submarine.modules.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.common.base.BaseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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
