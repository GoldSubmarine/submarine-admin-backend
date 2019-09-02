package org.javahub.submarine.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.Role;
import org.javahub.submarine.modules.system.entity.UserRole;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRole> {

    XPage<UserRole> findPage(XPage xPage, @Param("userRole") UserRole userRole);

    List<UserRole> findList(@Param("userRole") UserRole userRole);

    List<Role> getRoleById(@Param("userId") Long userId);

}
