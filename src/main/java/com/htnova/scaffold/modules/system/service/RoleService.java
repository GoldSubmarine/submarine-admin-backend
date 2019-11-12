package com.htnova.scaffold.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.scaffold.common.dto.XPage;
import com.htnova.scaffold.modules.system.entity.Permission;
import com.htnova.scaffold.modules.system.entity.Role;
import com.htnova.scaffold.modules.system.entity.RolePermission;
import com.htnova.scaffold.modules.system.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePermissionService rolePermissionService;

    @Transactional(readOnly = true)
    public XPage<Role> findRoleList(Role role, XPage xPage) {
        XPage<Role> roleXPage = roleMapper.findPage(xPage, role);
        return roleXPage;
    }

    @Transactional(readOnly = true)
    public List<Role> findRoleList(Role role) {
        return roleMapper.findList(role);
    }

    @Transactional
    public void saveRole(Role role) {
        super.saveOrUpdate(role);
    }

    @Transactional
    public Role getRoleById(long id) {
        List<Permission> permissionList = rolePermissionService.findPermissionList(Arrays.asList(id));
        Role role = roleMapper.selectById(id);
        role.setPermissionList(permissionList);
        return role;
    }

    @Transactional
    public void deleteRole(Long id) {
        rolePermissionService.remove(new LambdaQueryWrapper<>(new RolePermission()).eq(RolePermission::getRoleId, id));
        super.removeById(id);
    }
}
