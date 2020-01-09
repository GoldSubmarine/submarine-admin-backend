package com.htnova.security.controller;

import com.htnova.common.constant.ResultStatus;
import com.htnova.common.exception.ServiceException;
import com.htnova.common.util.JwtUtil;
import com.htnova.common.util.UserUtil;
import com.htnova.security.entity.JwtUser;
import com.htnova.system.manage.dto.UserDto;
import com.htnova.system.manage.entity.User;
import com.htnova.system.manage.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @menu 登录校验
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private UserService userService;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private JwtUtil jwtUtil;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public String login(@RequestBody UserDto userDto) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(userDto.getUsername());
        if(Objects.isNull(jwtUser)) {
            throw new ServiceException(ResultStatus.USER_NOT_EXIST);
        }
        if(!jwtUser.isEnabled()) {
            throw new ServiceException(ResultStatus.ACCOUNT_FREEZE);
        }
        if(!bCryptPasswordEncoder.matches(userDto.getPassword(), jwtUser.getPassword())) {
            throw new ServiceException(ResultStatus.PASSWORD_WRONG);
        }
        return jwtUtil.create(jwtUser);
    }

    /**
     * 用户信息
     */
    @GetMapping("/info")
    public JwtUser info() {
        User user = userService.getUserById(UserUtil.getJwtUser().getId());
        return JwtUser.createByUser(user);
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public void logout() {

    }

}
