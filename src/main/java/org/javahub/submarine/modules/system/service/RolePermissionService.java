package org.javahub.submarine.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.RolePermission;
import org.javahub.submarine.modules.system.mapper.RolePermissionMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames="RolePermissionService")
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermission> {

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Transactional(readOnly = true)
    @Cacheable
    public XPage<RolePermission> findRolePermissionList(RolePermission rolePermission, XPage xPage) {
        XPage<RolePermission> rolePermissionXPage = rolePermissionMapper.findPage(xPage, rolePermission);
        return rolePermissionXPage;
    }

    @Transactional(readOnly = true)
    @Cacheable
    public List<RolePermission> findRolePermissionList(RolePermission rolePermission) {
        return rolePermissionMapper.findList(rolePermission);
    }

    /**
     * 保存角色的权限
     */
    @Transactional
    @CacheEvict(allEntries = true)
    public void saveRolePermission(long roleId, List<Long> permissionIdList) {
        // 删除旧的
        super.remove(new QueryWrapper<>(new RolePermission()).lambda().eq(RolePermission::getRoleId, roleId));
        List<RolePermission> rolePermissionList = permissionIdList.stream()
                .map(item -> RolePermission.builder().permissionId(item).roleId(roleId).build())
                .collect(Collectors.toList());
        super.saveBatch(rolePermissionList);
    }

    @Transactional
    @Cacheable
    public RolePermission getRolePermissionById(Long id) {
        return rolePermissionMapper.selectById(id);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void deleteRolePermission(Long id) {
        super.removeById(id);
    }
}
