package com.htnova.system.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.common.base.BaseEntity;
import com.htnova.system.manage.dto.PermissionDto;
import com.htnova.system.manage.entity.Permission;
import com.htnova.system.manage.entity.RolePermission;
import com.htnova.system.manage.mapper.PermissionMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {
    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RolePermissionService rolePermissionService;

    @Transactional(readOnly = true)
    public IPage<Permission> findPermissionList(PermissionDto permissionDto, IPage<Void> xPage) {
        return permissionMapper.findPage(xPage, permissionDto);
    }

    @Transactional(readOnly = true)
    public List<Permission> findPermissionList(PermissionDto permissionDto) {
        return permissionMapper.findList(permissionDto);
    }

    @Transactional
    public void savePermission(Permission permission) {
        Permission parent = super.getById(permission.getPid());
        if (parent != null) {
            permission.setPids(
                String.format("%s%s,", Optional.ofNullable(parent.getPids()).orElse(""), parent.getId())
            );
        }
        super.saveOrUpdate(permission);
    }

    @Transactional
    public Permission getPermissionById(Long id) {
        return permissionMapper.selectById(id);
    }

    @Transactional
    public void deletePermission(long id) {
        // 删除子级和自身
        List<Permission> permissionList = super.lambdaQuery().like(Permission::getPids, id).list();
        List<Long> ids = permissionList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        ids.add(id);
        super.removeByIds(ids);
        // 删除角色的关联表
        rolePermissionService.remove(
            Wrappers.lambdaQuery(new RolePermission()).in(RolePermission::getPermissionId, ids)
        );
    }
}
