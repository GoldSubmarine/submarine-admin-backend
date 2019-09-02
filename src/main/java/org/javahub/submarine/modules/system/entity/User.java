package org.javahub.submarine.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.base.BaseEntity;
import org.javahub.submarine.modules.system.dto.UserDto;
import org.javahub.submarine.modules.system.mapstruct.UserMapStruct;
import org.mapstruct.factory.Mappers;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User extends BaseEntity {

    /**
     * 登录名
     */
    private String username;

    /**
     * 密码
     */
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
    private String status;

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
    private String jwtSecret;

    /**
     * 角色
     */
    @TableField(exist = false)
    private List<Role> roleList;

    public UserDto toDto() {
        UserMapStruct mapStruct = Mappers.getMapper( UserMapStruct.class );
        return mapStruct.toDto(this);
    }

}
