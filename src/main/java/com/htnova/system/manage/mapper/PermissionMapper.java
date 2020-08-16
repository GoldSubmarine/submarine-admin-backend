package com.htnova.system.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.manage.dto.PermissionDto;
import com.htnova.system.manage.entity.Permission;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermissionMapper extends BaseMapper<Permission> {
    IPage<Permission> findPage(IPage<Void> xPage, @Param("permissionDto") PermissionDto permissionDto);

    List<Permission> findList(@Param("permissionDto") PermissionDto permissionDto);
}
