package com.htnova.system.manage.dto;

import com.htnova.common.base.BaseTreeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeptDto extends BaseTreeDto<DeptDto> {

    /**
     * 名称（中文）
     */
    private String name;

    /**
     * 编码
     */
    private String code;

}
