package com.htnova.system.manage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.htnova.common.base.BaseDto;
import com.htnova.system.manage.entity.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class UserDto extends BaseDto {
    /** 登录名 */
    private String username;

    /** 密码 */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /** 姓名 */
    private String name;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 地址 */
    private String address;

    /** 性别 */
    private String sex;

    /** 头像 */
    private String avatar;

    /** 状态（启用禁用） */
    private User.UserStatus status;

    /** 部门id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;

    /** 部门ids（包含自身） */
    private String deptIds;

    /** 部门名称 */
    private String deptName;

    /** 角色 */
    private List<RoleDto> roleList;

    /** 角色id逗号分隔 */
    private String roleIds;

    /** 角色id */
    private List<Long> roleIdList;

    /** 权限 */
    private List<PermissionDto> permissionList;
}
