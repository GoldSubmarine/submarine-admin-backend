package org.javahub.submarine.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.*;
import org.javahub.submarine.modules.system.mapper.RoleMapper;
import org.javahub.submarine.modules.system.mapper.RoleMenuMapper;
import org.javahub.submarine.modules.system.mapper.RolePermissionMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private RoleMenuService roleMenuService;

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
        List<Permission> permissionList = rolePermissionMapper.getByRoleId(id);
        List<Menu> menuList = roleMenuMapper.getByRoleId(id);
        Role role = roleMapper.selectById(id);
        role.setPermissionList(permissionList);
        role.setMenuList(menuList);
        return role;
    }

    @Transactional
    public void deleteRole(Long id) {
        rolePermissionService.remove(new LambdaQueryWrapper<>(new RolePermission()).eq(RolePermission::getRoleId, id));
        roleMenuService.remove(new LambdaQueryWrapper<>(new RoleMenu()).eq(RoleMenu::getRoleId, id));
        super.removeById(id);
    }
}
