package com.htnova.system.manage.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.manage.dto.PermissionDto;
import com.htnova.system.manage.entity.Permission;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionMapStruct extends BaseMapStruct<PermissionDto, Permission> {

}
