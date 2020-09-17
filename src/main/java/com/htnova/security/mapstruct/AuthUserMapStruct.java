package com.htnova.security.mapstruct;

import com.htnova.security.entity.AuthUser;
import com.htnova.system.manage.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthUserMapStruct {
    AuthUserMapStruct INSTANCE = Mappers.getMapper(AuthUserMapStruct.class);

    AuthUser toAuthUser(User user);
}
