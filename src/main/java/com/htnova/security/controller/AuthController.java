package com.htnova.security.controller;

import com.htnova.common.util.UserUtil;
import com.htnova.security.entity.AuthUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** @menu 登录校验 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    /** 用户信息 */
    @GetMapping("/info")
    public AuthUser info() {
        return UserUtil.getAuthUser();
    }
}
