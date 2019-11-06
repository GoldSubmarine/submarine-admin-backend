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
