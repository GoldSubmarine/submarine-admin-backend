package com.htnova.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.common.dto.XPage;
import com.htnova.system.entity.Permission;
import com.htnova.system.entity.RolePermission;
import com.htnova.system.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermission> {

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Transactional(readOnly = true)
    public XPage<RolePermission> findRolePermissionList(RolePermission rolePermission, XPage xPage) {
        XPage<RolePermission> rolePermissionXPage = rolePermissionMapper.findPage(xPage, rolePermission);
        return rolePermissionXPage;
    }

    @Transactional(readOnly = true)
    public List<RolePermission> findRolePermissionList(RolePermission rolePermission) {
        return rolePermissionMapper.findList(rolePermission);
    }

    @Transactional
    public List<Permission> findPermissionList(List<Long> roleIds, Permission.PermissionType permissionType) {
        return findPermissionList(roleIds).stream().filter(item -> permissionType.equals(item.getType())).collect(Collectors.toList());
    }

    @Transactional
    public List<Permission> findPermissionList(List<Long> roleIds) {
        if(CollectionUtils.isEmpty(roleIds)) {
            return Arrays.asList();
        }
        return rolePermissionMapper.getByRoleIds(roleIds);
    }

    /**
     * 保存角色的权限
     */
    @Transactional
    public void saveRolePermission(long roleId, Permission.PermissionType type, List<Long> permissionIdList) {
        // 删除旧的
        super.remove(new QueryWrapper<>(new RolePermission()).lambda().eq(RolePermission::getRoleId, roleId).eq(RolePermission::getType, type));
        List<RolePermission> rolePermissionList = permissionIdList.stream()
                .map(item -> RolePermission.builder().permissionId(item).roleId(roleId).type(type).build())
                .collect(Collectors.toList());
        super.saveBatch(rolePermissionList);
    }

    @Transactional
    public RolePermission getRolePermissionById(Long id) {
        return rolePermissionMapper.selectById(id);
    }

    @Transactional
    public void deleteRolePermission(Long id) {
        super.removeById(id);
    }
}
