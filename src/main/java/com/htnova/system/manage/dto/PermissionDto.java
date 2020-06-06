package com.htnova.system.manage.dto;

import com.htnova.common.base.BaseTreeDto;
import com.htnova.system.manage.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class PermissionDto extends BaseTreeDto<PermissionDto> {
    /** 类型 */
    private Permission.PermissionType type;

    /** 前端类型 */
    private Permission.FrontType frontType;

    /** 名称（中文） */
    private String name;

    /** 权限值 */
    private String value;
}
