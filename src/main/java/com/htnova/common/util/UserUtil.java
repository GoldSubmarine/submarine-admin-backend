package com.htnova.common.util;

import com.htnova.security.entity.AuthUser;
import com.htnova.security.entity.UserDetail;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    private UserUtil() {}

    public static AuthUser getAuthUser() {
        return ((UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthUser();
    }

}
