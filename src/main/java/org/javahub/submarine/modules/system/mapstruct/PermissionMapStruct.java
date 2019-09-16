package org.javahub.submarine.modules.system.mapstruct;

import org.javahub.submarine.common.base.BaseMapStruct;
import org.javahub.submarine.modules.system.dto.PermissionDto;
import org.javahub.submarine.modules.system.entity.Permission;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionMapStruct extends BaseMapStruct<PermissionDto, Permission> {

}
