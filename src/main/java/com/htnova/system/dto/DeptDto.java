package com.htnova.system.dto;

import com.htnova.common.base.BaseTree;
import com.htnova.system.entity.Dept;
import com.htnova.system.mapstruct.DeptMapStruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeptDto extends BaseTree<DeptDto> {

    /**
     * 名称（中文）
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    public static Dept toEntity(DeptDto deptDto) {
        DeptMapStruct mapStruct = Mappers.getMapper( DeptMapStruct.class );
        return mapStruct.toEntity(deptDto);
    }

    public static DeptDto toDto(Dept dept) {
        DeptMapStruct mapStruct = Mappers.getMapper( DeptMapStruct.class );
        return mapStruct.toDto(dept);
    }

}
