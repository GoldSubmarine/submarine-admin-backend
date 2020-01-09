package com.htnova.system.manage.dto;

import com.htnova.common.base.BaseDto;
import com.htnova.system.manage.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleDto extends BaseDto {


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
    private Role.DisplayType orgAdminDisplay;

    /**
     * 权限
     */
    private List<PermissionDto> permissionList;

    /**
     * 菜单
     */
    private List<PermissionDto> menuList;

}
