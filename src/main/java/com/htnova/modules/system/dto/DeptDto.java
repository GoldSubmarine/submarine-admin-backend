package com.htnova.modules.system.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.htnova.common.base.BaseDto;
import com.htnova.modules.system.entity.Dept;
import com.htnova.modules.system.mapstruct.DeptMapStruct;
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
public class DeptDto extends BaseDto {

    /**
     * 名称（中文）
     */
    private String name;


    /**
     * 编码
     */
    private String code;

    /**
     * 父级id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long pid;

    /**
     * 子节点
     */
    private List<DeptDto> children;

    public static Dept toEntity(DeptDto deptDto) {
        DeptMapStruct mapStruct = Mappers.getMapper( DeptMapStruct.class );
        return mapStruct.toEntity(deptDto);
    }

    public static DeptDto toDto(Dept dept) {
        DeptMapStruct mapStruct = Mappers.getMapper( DeptMapStruct.class );
        return mapStruct.toDto(dept);
    }

}
