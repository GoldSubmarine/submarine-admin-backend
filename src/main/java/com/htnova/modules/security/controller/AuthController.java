package com.htnova.modules.security.controller;

import com.htnova.common.util.UserUtil;
import com.htnova.common.exception.ServiceException;
import com.htnova.common.util.JwtUtil;
import com.htnova.modules.security.entity.JwtUser;
import com.htnova.modules.system.dto.UserDto;
import com.htnova.modules.system.entity.User;
import com.htnova.modules.system.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String login(UserDto userDto) {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(userDto.getUsername());
        if(Objects.isNull(jwtUser)) {
            throw new ServiceException("用户不存在");
        }
        if(!jwtUser.isEnabled()) {
            throw new ServiceException("当前账号已冻结，请联系管理员");
        }
        if(!bCryptPasswordEncoder.matches(userDto.getPassword(), jwtUser.getPassword())) {
            throw new ServiceException("密码错误");
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
