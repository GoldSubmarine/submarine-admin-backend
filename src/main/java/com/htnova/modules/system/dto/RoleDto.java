package com.htnova.modules.system.dto;

import com.htnova.common.base.BaseDto;
import com.htnova.modules.system.entity.Role;
import com.htnova.modules.system.mapstruct.RoleMapStruct;
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
