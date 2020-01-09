package com.htnova.security.entity;

import com.htnova.security.mapstruct.AuthUserMapStruct;
import com.htnova.system.manage.entity.Permission;
import com.htnova.system.manage.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * oauth server 用的 UserDetail（权限为oauth server的GrantedAuthority）
 * 同时也保存了原始的User（包括了基本信息，以及全部的权限信息 Role、Permission）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getPermissionList().stream()
                .filter(item -> Permission.PermissionType.button == item.getType())
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return User.UserStatus.enable.equals(user.getStatus());
    }

    public static UserDetail createByUser(User user){
        return  new UserDetail(user);
    }

    public AuthUser getAuthUser(){
        AuthUserMapStruct mapper = Mappers.getMapper(AuthUserMapStruct.class);
        return mapper.toAuthUser(user);
    }

}
