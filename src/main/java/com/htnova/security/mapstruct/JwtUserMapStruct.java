package com.htnova.security.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.security.entity.JwtUser;
import com.htnova.system.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface JwtUserMapStruct extends BaseMapStruct<JwtUser, User> {

}
