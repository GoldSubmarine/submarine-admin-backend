package org.javahub.submarine.modules.system.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.common.base.BaseDto;
import org.javahub.submarine.modules.system.entity.Permission;
import org.javahub.submarine.modules.system.mapstruct.PermissionMapStruct;
import org.mapstruct.factory.Mappers;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PermissionDto extends BaseDto {

    /**
     * 名称（中文）
     */
    private String name;


    /**
     * 权限值
     */
    private String value;

    /**
     * 父级id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long pid;

    /**
     * 子节点
     */
    private List<PermissionDto> children;

    public static Permission toEntity(PermissionDto permissionDto) {
        PermissionMapStruct mapStruct = Mappers.getMapper( PermissionMapStruct.class );
        return mapStruct.toEntity(permissionDto);
    }

    public static PermissionDto toDto(Permission permission) {
        PermissionMapStruct mapStruct = Mappers.getMapper( PermissionMapStruct.class );
        return mapStruct.toDto(permission);
    }

}
