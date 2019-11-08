package com.htnova.scaffold.modules.security.service;

import com.htnova.scaffold.common.exception.ServiceException;
import com.htnova.scaffold.modules.security.entity.JwtUser;
import com.htnova.scaffold.modules.system.entity.User;
import com.htnova.scaffold.modules.system.service.UserService;
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
        if(Objects.isNull(user)) {
            throw new ServiceException("账号不存在");
        }
        return JwtUser.createByUser(user);
    }
}
