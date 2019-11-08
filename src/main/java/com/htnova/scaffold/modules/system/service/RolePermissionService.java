package com.htnova.scaffold.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.scaffold.common.dto.XPage;
import com.htnova.scaffold.modules.system.entity.RolePermission;
import com.htnova.scaffold.modules.system.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    /**
     * 保存角色的权限
     */
    @Transactional
    public void saveRolePermission(long roleId, List<Long> permissionIdList) {
        // 删除旧的
        super.remove(new QueryWrapper<>(new RolePermission()).lambda().eq(RolePermission::getRoleId, roleId));
        List<RolePermission> rolePermissionList = permissionIdList.stream()
                .map(item -> RolePermission.builder().permissionId(item).roleId(roleId).build())
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
