package com.htnova.scaffold.modules.security.mapstruct;

import com.htnova.scaffold.common.base.BaseMapStruct;
import com.htnova.scaffold.modules.security.entity.JwtUser;
import com.htnova.scaffold.modules.system.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface JwtUserMapStruct extends BaseMapStruct<JwtUser, User> {

}
