package com.htnova.system.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.system.manage.dto.RolePermissionDto;
import com.htnova.system.manage.dto.RolePermissionDto.BatchAuthType;
import com.htnova.system.manage.entity.Permission;
import com.htnova.system.manage.entity.RolePermission;
import com.htnova.system.manage.mapper.RolePermissionMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermission> {
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Transactional(readOnly = true)
    public IPage<RolePermission> findRolePermissionList(RolePermissionDto rolePermissionDto, IPage<Void> xPage) {
        return rolePermissionMapper.findPage(xPage, rolePermissionDto);
    }

    @Transactional(readOnly = true)
    public List<RolePermission> findRolePermissionList(RolePermissionDto rolePermissionDto) {
        return rolePermissionMapper.findList(rolePermissionDto);
    }

    @Transactional
    public List<Permission> findPermissionList(List<Long> roleIds, Permission.PermissionType permissionType) {
        return findPermissionList(roleIds)
            .stream()
            .filter(item -> permissionType.equals(item.getType()))
            .collect(Collectors.toList());
    }

    @Transactional
    public List<Permission> findPermissionList(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return rolePermissionMapper.getByRoleIds(roleIds);
    }

    /** 保存角色的权限 */
    @Transactional
    public void saveRolePermission(long roleId, List<Long> permissionIdList) {
        // 删除旧的
        super.remove(Wrappers.lambdaQuery(new RolePermission()).eq(RolePermission::getRoleId, roleId));
        List<RolePermission> rolePermissionList = permissionIdList
            .stream()
            .map(item -> RolePermission.builder().permissionId(item).roleId(roleId).build())
            .collect(Collectors.toList());
        super.saveBatch(rolePermissionList);
    }

    /** 批量保存角色的权限 */
    @Transactional
    public void saveRolePermissionBatch(BatchAuthType batchAuthType, String roleIds, List<Long> permissionIdList) {
        List<Long> roleIdList = Arrays
            .stream(StringUtils.split(roleIds, ","))
            .map(Long::parseLong)
            .collect(Collectors.toList());
        // 删除旧的
        super.remove(
            Wrappers
                .lambdaQuery(new RolePermission())
                .in(RolePermission::getRoleId, roleIdList)
                .in(RolePermission::getPermissionId, permissionIdList)
        );
        // 如果是批量新增，保存新的
        if (BatchAuthType.auth.equals(batchAuthType)) {
            List<RolePermission> rolePermissionList = new ArrayList<>();
            roleIdList.forEach(
                roleId ->
                    permissionIdList.forEach(
                        permissionId ->
                            rolePermissionList.add(
                                RolePermission.builder().permissionId(permissionId).roleId(roleId).build()
                            )
                    )
            );
            super.saveBatch(rolePermissionList);
        }
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
