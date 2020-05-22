package com.htnova.system.manage.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.manage.dto.RoleDto;
import com.htnova.system.manage.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapStruct extends BaseMapStruct<RoleDto, Role> {}
