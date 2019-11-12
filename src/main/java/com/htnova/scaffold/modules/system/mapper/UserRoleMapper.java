package com.htnova.scaffold.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.htnova.scaffold.common.dto.XPage;
import com.htnova.scaffold.modules.system.entity.Role;
import com.htnova.scaffold.modules.system.entity.UserRole;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRole> {

    XPage<UserRole> findPage(XPage xPage, @Param("userRole") UserRole userRole);

    List<UserRole> findList(@Param("userRole") UserRole userRole);

    List<Role> getRoleByUserId(@Param("userId") Long userId);

}
