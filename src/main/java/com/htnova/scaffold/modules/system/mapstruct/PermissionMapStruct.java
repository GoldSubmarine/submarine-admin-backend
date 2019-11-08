package com.htnova.scaffold.modules.system.mapstruct;

import com.htnova.scaffold.common.base.BaseMapStruct;
import com.htnova.scaffold.modules.system.dto.PermissionDto;
import com.htnova.scaffold.modules.system.entity.Permission;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionMapStruct extends BaseMapStruct<PermissionDto, Permission> {

}
