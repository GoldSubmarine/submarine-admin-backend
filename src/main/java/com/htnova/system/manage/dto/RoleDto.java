package com.htnova.system.manage.dto;

import com.htnova.common.base.BaseDto;
import com.htnova.system.manage.entity.Role;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class RoleDto extends BaseDto {
    /** 名称（中文） */
    private String name;

    /** 编码 */
    private String code;

    /** 机构管理员是否可见 */
    private Role.DisplayType orgAdminDisplay;

    /** 权限 */
    private List<PermissionDto> permissionList;
}
