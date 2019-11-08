package com.htnova.scaffold.modules.system.mapstruct;

import com.htnova.scaffold.common.base.BaseMapStruct;
import com.htnova.scaffold.modules.system.dto.RoleDto;
import com.htnova.scaffold.modules.system.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapStruct extends BaseMapStruct<RoleDto, Role> {

}
