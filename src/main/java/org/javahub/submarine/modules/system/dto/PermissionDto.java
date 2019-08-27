package org.javahub.submarine.modules.system.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.base.BaseDto;
import org.javahub.submarine.common.util.CommonUtil;
import org.javahub.submarine.modules.system.entity.Permission;

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

    public Permission toEntity() {
        Permission permission = super.copyProperties(Permission.class);
        permission.setChildren(CommonUtil.toEntity(children));
        return permission;
    }

}
