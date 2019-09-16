package org.javahub.submarine.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.common.base.BaseEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Role extends BaseEntity {

    /**
     * 名称（中文）
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 权限
     */
    @TableField(exist=false)
    private List<Permission> permissionList;

    /**
     * 菜单
     */
    @TableField(exist=false)
    private List<Menu> menuList;

    public enum RoleCode {
        SuperAdmin,
        Admin;
    }

}
