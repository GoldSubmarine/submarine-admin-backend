package com.htnova.modules.security.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.modules.security.entity.JwtUser;
import com.htnova.modules.system.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface JwtUserMapStruct extends BaseMapStruct<JwtUser, User> {

}
