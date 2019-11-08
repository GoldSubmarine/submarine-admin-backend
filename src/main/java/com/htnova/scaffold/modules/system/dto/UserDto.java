package com.htnova.scaffold.modules.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.htnova.scaffold.common.base.BaseDto;
import com.htnova.scaffold.modules.system.entity.User;
import com.htnova.scaffold.modules.system.mapstruct.UserMapStruct;
import org.mapstruct.factory.Mappers;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto extends BaseDto {

    /**
     * 登录名
     */
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 性别
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态（启用禁用）
     */
    private User.UserStatus status;

    /**
     * 部门id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /**
     * 部门ids（包含自身）
     */
    private String deptIds;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 角色
     */
    private List<RoleDto> roleList;

    /**
     * 角色id逗号分隔
     */
    private String roleIdList;

    /**
     * 权限
     */
    private List<PermissionDto> permissionList;

    public static User toEntity(UserDto userDto) {
        UserMapStruct mapStruct = Mappers.getMapper( UserMapStruct.class );
        return mapStruct.toEntity(userDto);
    }

    public static UserDto toDto(User user) {
        UserMapStruct mapStruct = Mappers.getMapper( UserMapStruct.class );
        return mapStruct.toDto(user);
    }

}
