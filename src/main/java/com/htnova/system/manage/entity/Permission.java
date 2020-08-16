package com.htnova.system.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.htnova.common.base.BaseTreeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@TableName("t_sys_permission")
public class Permission extends BaseTreeEntity<Permission> {
    /** 类型 */
    private PermissionType type;

    /** 前端类型 */
    private FrontType frontType;

    /** 名称（中文） */
    private String name;

    /** 权限值 */
    private String value;

    public enum PermissionType {
        /** 菜单 */
        menu,

        /** 按钮 */
        button,
    }

    public enum FrontType {
        WEB,
        APP,
    }
}
