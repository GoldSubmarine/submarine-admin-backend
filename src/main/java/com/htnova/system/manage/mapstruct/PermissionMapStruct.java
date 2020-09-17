package com.htnova.system.manage.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.manage.dto.PermissionDto;
import com.htnova.system.manage.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PermissionMapStruct extends BaseMapStruct<PermissionDto, Permission> {
    PermissionMapStruct INSTANCE = Mappers.getMapper(PermissionMapStruct.class);
}
