package com.htnova.system.manage.dto;

import com.htnova.common.base.BaseTreeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class DeptDto extends BaseTreeDto<DeptDto> {
    /** 名称（中文） */
    private String name;

    /** 编码 */
    private String code;
}
