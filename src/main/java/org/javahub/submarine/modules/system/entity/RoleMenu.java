package org.javahub.submarine.modules.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.base.BaseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleMenu extends BaseEntity {

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 角色id
     */
    private Long roleId;

}
