package com.htnova.system.dto;

import com.htnova.common.base.BaseTree;
import com.htnova.system.entity.Permission;
import com.htnova.system.mapstruct.PermissionMapStruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PermissionDto extends BaseTree<PermissionDto> {

    /**
     * 类型
     */
    private Permission.PermissionType type;

    /**
     * 名称（中文）
     */
    private String name;

    /**
     * 权限值
     */
    private String value;

    public static Permission toEntity(PermissionDto permissionDto) {
        PermissionMapStruct mapStruct = Mappers.getMapper( PermissionMapStruct.class );
        return mapStruct.toEntity(permissionDto);
    }

    public static PermissionDto toDto(Permission permission) {
        PermissionMapStruct mapStruct = Mappers.getMapper( PermissionMapStruct.class );
        return mapStruct.toDto(permission);
    }

}
