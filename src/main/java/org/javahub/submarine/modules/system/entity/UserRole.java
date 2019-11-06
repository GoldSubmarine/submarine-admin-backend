package org.javahub.submarine.modules.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.javahub.submarine.common.base.BaseEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class UserRole extends BaseEntity {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色列表
     */
    private List<Role> roleList;

}
