package com.htnova.system.tool.dto;

import com.htnova.common.base.BaseTreeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class LocationDto extends BaseTreeDto<LocationDto> {
    /** 当前层次 */
    private Integer deep;

    /** 区域名 */
    private String name;

    /** 区域中心经纬度 */
    private String point;
}
