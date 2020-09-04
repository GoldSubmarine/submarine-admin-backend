package com.htnova.common.util;

import com.htnova.security.entity.AuthUser;
import java.util.Objects;

public class UserUtil {
    private static final ThreadLocal<AuthUser> socketUserThreadLocal = new ThreadLocal<>();

    private UserUtil() {}

    public static AuthUser getAuthUser() {
        AuthUser authUser = SpringContextUtil.getAuthUser();
        if (Objects.nonNull(authUser)) {
            return authUser;
        }
        if (Objects.nonNull(socketUserThreadLocal.get())) {
            return socketUserThreadLocal.get();
        }
        return new AuthUser();
    }

    public static boolean hasAuthUser() {
        return getAuthUser().getId() != null;
    }

    public static void setAuthUser(AuthUser authUser) {
        socketUserThreadLocal.set(authUser);
    }

    public static void clearAuthUser() {
        socketUserThreadLocal.remove();
    }
}
