package com.htnova.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.dto.RoleDto;
import com.htnova.system.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    IPage<Role> findPage(IPage<Void> xPage, @Param("roleDto") RoleDto roleDto);

    List<Role> findList(@Param("roleDto") RoleDto roleDto);

}
