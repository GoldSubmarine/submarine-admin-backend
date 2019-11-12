package com.htnova.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.common.base.BaseEntity;
import com.htnova.common.dto.XPage;
import com.htnova.modules.system.entity.Permission;
import com.htnova.modules.system.entity.RolePermission;
import com.htnova.modules.system.mapper.PermissionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RolePermissionService rolePermissionService;

    @Transactional(readOnly = true)
    public XPage<Permission> findPermissionList(Permission permission, XPage xPage) {
        XPage<Permission> permissionXPage = permissionMapper.findPage(xPage, permission);
        return permissionXPage;
    }

    @Transactional(readOnly = true)
    public List<Permission> findPermissionList(Permission permission) {
        return permissionMapper.findList(permission);
    }

    @Transactional
    public void savePermission(Permission permission) {
        Permission parent = super.getById(permission.getPid());
        if(parent != null) {
            permission.setPids(String.format("%s%s,", Optional.ofNullable(parent.getPids()).orElse(""), parent.getId()));
        }
        super.saveOrUpdate(permission);
    }

    @Transactional
    public Permission getPermissionById(Long id) {
        return permissionMapper.selectById(id);
    }

    @Transactional
    public void deletePermission(long id) {
        // 删除角色的关联表
        rolePermissionService.remove(new LambdaQueryWrapper<>(new RolePermission()).eq(RolePermission::getPermissionId, id));
        // 删除子级和自身
        List<Permission> permissionList = super.lambdaQuery().like(Permission::getPids, id).list();
        List<Long> ids = permissionList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        ids.add(id);
        super.removeByIds(ids);
    }
}