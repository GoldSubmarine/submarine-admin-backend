package org.javahub.submarine.modules.system.mapstruct;

import org.javahub.submarine.base.BaseMapStruct;
import org.javahub.submarine.modules.system.dto.UserDto;
import org.javahub.submarine.modules.system.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapStruct extends BaseMapStruct<UserDto, User> {

}
