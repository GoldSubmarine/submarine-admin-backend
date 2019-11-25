package com.htnova.system.dto;

import com.htnova.common.base.BaseEntity;
import com.htnova.system.entity.Role;
import com.htnova.system.mapstruct.RoleMapStruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleDto extends BaseEntity {


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

    public static Role toEntity(RoleDto roleDto) {
        RoleMapStruct mapStruct = Mappers.getMapper( RoleMapStruct.class );
        return mapStruct.toEntity(roleDto);
    }

    public static RoleDto toDto(Role role) {
        RoleMapStruct mapStruct = Mappers.getMapper( RoleMapStruct.class );
        return mapStruct.toDto(role);
    }

}
