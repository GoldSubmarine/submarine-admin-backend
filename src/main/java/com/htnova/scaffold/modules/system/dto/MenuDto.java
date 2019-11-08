package com.htnova.scaffold.modules.system.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.htnova.scaffold.common.base.BaseDto;
import com.htnova.scaffold.modules.system.entity.Menu;
import com.htnova.scaffold.modules.system.mapstruct.MenuMapStruct;
import org.mapstruct.factory.Mappers;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MenuDto extends BaseDto {

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
    private List<MenuDto> children;

    public static Menu toEntity(MenuDto menuDto) {
        MenuMapStruct mapStruct = Mappers.getMapper( MenuMapStruct.class );
        return mapStruct.toEntity(menuDto);
    }

    public static MenuDto toDto(Menu menu) {
        MenuMapStruct mapStruct = Mappers.getMapper( MenuMapStruct.class );
        return mapStruct.toDto(menu);
    }

}
