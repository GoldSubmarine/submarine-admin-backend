package org.javahub.submarine.common.util;

import org.javahub.submarine.modules.security.entity.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    public static JwtUser getJwtUser() {
        return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
