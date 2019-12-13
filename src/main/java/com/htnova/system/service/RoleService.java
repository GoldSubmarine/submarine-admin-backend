package com.htnova.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.common.util.UserUtil;
import com.htnova.system.entity.Permission;
import com.htnova.system.entity.Role;
import com.htnova.system.entity.RolePermission;
import com.htnova.system.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePermissionService rolePermissionService;

    @Transactional(readOnly = true)
    public IPage<Role> findRoleList(Role role, IPage<Void> xPage) {
        IPage<Role> roleXPage = roleMapper.findPage(xPage, role);
        return roleXPage;
    }

    private List<Role> filterRoleByCode(List<Role> roleList, String ...roleCodeList) {
        List<String> list = Arrays.asList(roleCodeList);
        return roleList.stream().filter(item -> !list.contains(item.getCode())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Role> findRoleList(Role role) {
        List<Role> roleList = roleMapper.findList(role);
        if(!UserUtil.getJwtUser().isSuperAdmin()) {
            roleList = filterRoleByCode(roleList, Role.SUPER_ADMIN_CODE);
        }
        if(UserUtil.getJwtUser().getRoles().contains(Role.ORG_ADMIN_CODE)) {
            roleList = filterRoleByCode(roleList, Role.ADMIN_CODE, Role.ORG_ADMIN_CODE);
            roleList = roleList.stream().filter(item -> Role.DisplayType.visible.equals(item.getOrgAdminDisplay())).collect(Collectors.toList());
        }
        return roleList;
    }

    @Transactional
    public void saveRole(Role role) {
        super.saveOrUpdate(role);
    }

    @Transactional
    public Role getRoleById(long id) {
        List<Permission> permissionList = rolePermissionService.findPermissionList(Collections.singletonList(id));
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
