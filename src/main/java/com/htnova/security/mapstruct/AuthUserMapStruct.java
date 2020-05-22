package com.htnova.security.mapstruct;

import com.htnova.security.entity.AuthUser;
import com.htnova.system.manage.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface AuthUserMapStruct {
    AuthUser toAuthUser(User user);
}
