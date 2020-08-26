package com.htnova.system.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.common.util.UserUtil;
import com.htnova.system.manage.dto.RoleDto;
import com.htnova.system.manage.entity.Permission;
import com.htnova.system.manage.entity.Role;
import com.htnova.system.manage.entity.Role.RoleCode;
import com.htnova.system.manage.entity.RolePermission;
import com.htnova.system.manage.mapper.RoleMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePermissionService rolePermissionService;

    @Transactional(readOnly = true)
    public IPage<Role> findRoleList(RoleDto roleDto, IPage<Void> xPage) {
        return roleMapper.findPage(xPage, roleDto);
    }

    private List<Role> filterRoleByCode(List<Role> roleList, String... roleCodeList) {
        List<String> list = Arrays.asList(roleCodeList);
        return roleList.stream().filter(item -> !list.contains(item.getCode())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Role> findRoleList(RoleDto roleDto) {
        List<Role> roleList = roleMapper.findList(roleDto);
        if (!UserUtil.getAuthUser().isSuperAdmin()) {
            roleList = filterRoleByCode(roleList, RoleCode.SuperAdmin.name());
        }
        if (UserUtil.getAuthUser().getRoles().contains(RoleCode.OrgAdmin.name())) {
            roleList = filterRoleByCode(roleList, RoleCode.Admin.name(), RoleCode.OrgAdmin.name());
            roleList =
                roleList
                    .stream()
                    .filter(item -> Role.DisplayType.visible.equals(item.getOrgAdminDisplay()))
                    .collect(Collectors.toList());
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
