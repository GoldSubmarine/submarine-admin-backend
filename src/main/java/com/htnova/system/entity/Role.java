package com.htnova.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.htnova.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class Role extends BaseEntity {

    // 超级管理员，开发人员
    public static final String SUPER_ADMIN_CODE = "SuperAdmin";
    // 系统管理员
    public static final String ADMIN_CODE = "Admin";
    // 机构管理员
    public static final String ORG_ADMIN_CODE = "OrgAdmin";

    /**
     * 名称（中文）
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 机构管理员是否可见
     */
    private DisplayType orgAdminDisplay;

    /**
     * 权限
     */
    @TableField(exist=false)
    private List<Permission> permissionList;

    public enum RoleCode {
        SuperAdmin,
        Admin;
    }

    public enum DisplayType {
        visible,
        hidden;
    }

}
