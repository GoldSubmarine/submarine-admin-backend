package com.htnova.modules.system.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.modules.system.dto.PermissionDto;
import com.htnova.modules.system.entity.Permission;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionMapStruct extends BaseMapStruct<PermissionDto, Permission> {

}
