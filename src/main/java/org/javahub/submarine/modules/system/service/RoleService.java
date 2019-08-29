package org.javahub.submarine.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.*;
import org.javahub.submarine.modules.system.mapper.RoleMapper;
import org.javahub.submarine.modules.system.mapper.RoleMenuMapper;
import org.javahub.submarine.modules.system.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 保存角色的权限
     */
    @Transactional
    public void saveRolePermission(long roleId, List<Long> permissionIdList) {
        // 删除旧的
        rolePermissionService.remove(new QueryWrapper<>(new RolePermission()).lambda().eq(RolePermission::getRoleId, roleId));
        List<RolePermission> rolePermissionList = permissionIdList.stream()
                .map(item -> RolePermission.builder().permissionId(item).roleId(roleId).build())
                .collect(Collectors.toList());
        rolePermissionService.saveBatch(rolePermissionList);
    }

    /**
     * 保存角色的菜单
     */
    @Transactional
    public void saveRoleMenu(long roleId, List<Long> menuIdList) {
        // 删除旧的
        roleMenuService.remove(new QueryWrapper<>(new RoleMenu()).lambda().eq(RoleMenu::getRoleId, roleId));
        List<RoleMenu> roleMenuList = menuIdList.stream()
                .map(item -> RoleMenu.builder().menuId(item).roleId(roleId).build())
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuList);
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
        super.removeById(id);
    }
}
