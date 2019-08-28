package org.javahub.submarine.modules.system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.base.BaseDto;
import org.javahub.submarine.modules.system.entity.User;
import org.javahub.submarine.modules.system.mapstruct.UserMapStruct;
import org.mapstruct.factory.Mappers;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto extends BaseDto {


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
    private String avatar;

    /**
     * 用户类型
     */
    private String type;

    /**
     * 状态（启用禁用）
     */
    private String status;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;


    public User toEntity() {
        UserMapStruct mapStruct = Mappers.getMapper( UserMapStruct.class );
        return mapStruct.toEntity(this);
    }

}
