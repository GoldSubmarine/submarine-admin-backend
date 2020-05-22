package com.htnova.system.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.manage.dto.RoleDto;
import com.htnova.system.manage.entity.Role;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper extends BaseMapper<Role> {
    IPage<Role> findPage(IPage<Void> xPage, @Param("roleDto") RoleDto roleDto);

    List<Role> findList(@Param("roleDto") RoleDto roleDto);
}
