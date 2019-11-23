package com.htnova.system.entity;

import com.htnova.common.base.BaseTree;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class Permission extends BaseTree<Permission> {

    /**
     * 类型
     */
    private PermissionType type;

    /**
     * 名称（中文）
     */
    private String name;

    /**
     * 权限值
     */
    private String value;

    public enum PermissionType {

        /**
         * 菜单
         */
        menu,

        /**
         * 按钮
         */
        button
    }

}
