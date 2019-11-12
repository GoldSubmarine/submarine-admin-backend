package com.htnova.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.htnova.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class User extends BaseEntity {

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
    private UserStatus status;

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
     * jwt密钥
     */
    @JsonIgnore
    private String jwtSecret;

    /**
     * 角色
     */
    @TableField(exist = false)
    private List<Role> roleList;

    /**
     * 权限
     */
    @TableField(exist = false)
    private List<Permission> permissionList;

    public enum UserStatus {
        enable,
        disable;
    }

}
