package com.htnova.common.util;

import com.htnova.security.entity.AuthUser;
import com.htnova.security.entity.UserDetail;
import com.htnova.security.mapstruct.AuthUserMapStruct;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

public class UserUtil {

    private UserUtil() {}

    public static AuthUser getAuthUser() {
        UserDetail userDetail = (UserDetail) Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication()).map(Authentication::getPrincipal).orElse(null);
        if(Objects.isNull(userDetail)) {
            return null;
        }
        AuthUserMapStruct mapper = Mappers.getMapper(AuthUserMapStruct.class);
        return mapper.toAuthUser(userDetail.getUser());
    }

}
