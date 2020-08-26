package com.htnova.system.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.htnova.common.base.BaseEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@TableName("t_sys_role")
public class Role extends BaseEntity {
    /** 名称（中文） */
    private String name;

    /** 编码 */
    private String code;

    /** 机构管理员是否可见 */
    private DisplayType orgAdminDisplay;

    /** 权限 */
    @TableField(exist = false)
    private List<Permission> permissionList;

    public enum RoleCode {
        // 超级管理员，开发人员
        SuperAdmin,
        // 系统管理员
        Admin,
        // 机构管理员
        OrgAdmin,
    }

    public enum DisplayType {
        visible,
        hidden,
    }
}
