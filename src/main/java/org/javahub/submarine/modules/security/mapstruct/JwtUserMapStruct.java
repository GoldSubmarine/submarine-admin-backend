package org.javahub.submarine.modules.security.mapstruct;

import org.javahub.submarine.common.base.BaseMapStruct;
import org.javahub.submarine.modules.security.entity.JwtUser;
import org.javahub.submarine.modules.system.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface JwtUserMapStruct extends BaseMapStruct<JwtUser, User> {

}
