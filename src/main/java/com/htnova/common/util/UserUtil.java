package com.htnova.common.util;

import com.htnova.modules.security.entity.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    public static JwtUser getJwtUser() {
        return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
