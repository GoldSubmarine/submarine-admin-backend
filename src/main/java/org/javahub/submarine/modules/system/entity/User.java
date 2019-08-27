package org.javahub.submarine.modules.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.base.BaseEntity;
import org.javahub.submarine.common.constant.GlobalConst;
import org.javahub.submarine.modules.system.dto.UserDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

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
    private String avatar = GlobalConst.DEFAULT_AVATAR;

    /**
     * 用户类型
     */
    private UserType type = UserType.staff;

    /**
     * 状态（启用禁用）
     */
    private StatusType status = StatusType.on;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 角色
     */
//    private List<Role> roleList;

    /**
     * 权限
     */
    private List<Permission> permissionList;


    public enum StatusType {
        /**
         * 启用
         */
        on,

        /**
         * 禁用
         */
        off
    }

    public enum UserType {
        /**
         * 超级管理员
         */
        superAdmin,

        /**
         * 员工
         */
        staff
    }

    public UserDto toDto() {
        UserDto userDto = super.copyProperties(UserDto.class);
        return userDto;
    }
}
