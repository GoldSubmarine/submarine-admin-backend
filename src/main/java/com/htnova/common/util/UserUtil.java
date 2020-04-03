package com.htnova.common.util;

import com.htnova.security.entity.AuthUser;
import com.htnova.security.entity.UserDetail;
import com.htnova.security.mapstruct.AuthUserMapStruct;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    private UserUtil() {}

    public static AuthUser getAuthUser() {
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUserMapStruct mapper = Mappers.getMapper(AuthUserMapStruct.class);
        return mapper.toAuthUser(userDetail.getUser());
    }

}
