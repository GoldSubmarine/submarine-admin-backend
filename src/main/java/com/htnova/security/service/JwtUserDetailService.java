package com.htnova.security.service;

import com.htnova.common.constant.ResultStatus;
import com.htnova.common.exception.ServiceException;
import com.htnova.security.entity.JwtUser;
import com.htnova.system.entity.User;
import com.htnova.system.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class JwtUserDetailService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);
        userService.fillRolePermission(user);
        if(Objects.isNull(user)) {
            throw new ServiceException(ResultStatus.USER_NOT_EXIST);
        }
        return JwtUser.createByUser(user);
    }
}
