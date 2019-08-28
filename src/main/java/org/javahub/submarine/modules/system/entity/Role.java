package org.javahub.submarine.modules.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.base.BaseEntity;
import org.javahub.submarine.modules.system.dto.RoleDto;
import org.javahub.submarine.modules.system.mapstruct.RoleMapStruct;
import org.mapstruct.factory.Mappers;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Role extends BaseEntity {

    /**
     * 名称（中文）
     */
    private String name;

    /**
     * 编码
     */
    private String code;


    public RoleDto toDto() {
        RoleMapStruct mapStruct = Mappers.getMapper( RoleMapStruct.class );
        return mapStruct.toDto(this);
    }

}
