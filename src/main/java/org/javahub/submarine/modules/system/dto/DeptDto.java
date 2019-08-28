package org.javahub.submarine.modules.system.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.base.BaseDto;
import org.javahub.submarine.modules.system.entity.Dept;
import org.javahub.submarine.modules.system.mapstruct.DeptMapStruct;
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

    public Dept toEntity() {
        DeptMapStruct mapStruct = Mappers.getMapper( DeptMapStruct.class );
        return mapStruct.toEntity(this);
    }

}
