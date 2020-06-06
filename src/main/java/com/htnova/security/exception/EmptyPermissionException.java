package com.htnova.security.exception;

import org.springframework.security.core.AuthenticationException;

public class EmptyPermissionException extends AuthenticationException {

    public EmptyPermissionException(String msg, Throwable t) {
        super(msg, t);
    }

    public EmptyPermissionException(String msg) {
        super(msg);
    }
}
