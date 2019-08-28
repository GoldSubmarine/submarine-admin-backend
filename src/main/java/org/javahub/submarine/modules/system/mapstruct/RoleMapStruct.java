package org.javahub.submarine.modules.system.mapstruct;

import org.javahub.submarine.base.BaseMapStruct;
import org.javahub.submarine.modules.system.dto.RoleDto;
import org.javahub.submarine.modules.system.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapStruct extends BaseMapStruct<RoleDto, Role> {

}
