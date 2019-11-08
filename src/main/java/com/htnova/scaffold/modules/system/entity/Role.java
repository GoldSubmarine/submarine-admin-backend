package com.htnova.scaffold.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.htnova.scaffold.common.base.BaseEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class Role extends BaseEntity {

    /**
     * 名称（中文）
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 权限
     */
    @TableField(exist=false)
    private List<Permission> permissionList;

    /**
     * 菜单
     */
    @TableField(exist=false)
    private List<Menu> menuList;

    public enum RoleCode {
        SuperAdmin,
        Admin;
    }

}
