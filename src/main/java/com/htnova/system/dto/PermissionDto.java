package com.htnova.system.dto;

import com.htnova.common.base.BaseTreeDto;
import com.htnova.system.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PermissionDto extends BaseTreeDto<PermissionDto> {

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

}
