package com.htnova.system.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.manage.entity.Role;
import com.htnova.system.manage.entity.UserRole;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper extends BaseMapper<UserRole> {
    IPage<UserRole> findPage(IPage<Void> xPage, @Param("userRole") UserRole userRole);

    List<UserRole> findList(@Param("userRole") UserRole userRole);

    List<Role> getRoleByUserId(@Param("userId") Long userId);
}
