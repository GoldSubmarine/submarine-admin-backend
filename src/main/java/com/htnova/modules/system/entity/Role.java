package com.htnova.modules.system.entity;

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

    public enum RoleCode {
        SuperAdmin,
        Admin;
    }

}
