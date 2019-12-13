package com.htnova.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.dto.PermissionDto;
import com.htnova.system.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    IPage<Permission> findPage(IPage<Void> xPage, @Param("permissionDto") PermissionDto permissionDto);

    List<Permission> findList(@Param("permissionDto") PermissionDto permissionDto);

}
