package com.htnova.scaffold.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.scaffold.common.dto.XPage;
import com.htnova.scaffold.modules.system.entity.RoleMenu;
import com.htnova.scaffold.modules.system.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleMenuService extends ServiceImpl<RoleMenuMapper, RoleMenu> {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Transactional(readOnly = true)
    public XPage<RoleMenu> findRoleMenuList(RoleMenu roleMenu, XPage xPage) {
        XPage<RoleMenu> roleMenuXPage = roleMenuMapper.findPage(xPage, roleMenu);
        return roleMenuXPage;
    }

    @Transactional(readOnly = true)
    public List<RoleMenu> findRoleMenuList(RoleMenu roleMenu) {
        return roleMenuMapper.findList(roleMenu);
    }

    /**
     * 保存角色的菜单
     */
    @Transactional
    public void saveRoleMenu(long roleId, List<Long> menuIdList) {
        // 删除旧的
        super.remove(new LambdaQueryWrapper<>(new RoleMenu()).eq(RoleMenu::getRoleId, roleId));
        List<RoleMenu> roleMenuList = menuIdList.stream()
                .map(item -> RoleMenu.builder().menuId(item).roleId(roleId).build())
                .collect(Collectors.toList());
        super.saveBatch(roleMenuList);
    }

    @Transactional
    public RoleMenu getRoleMenuById(long id) {
        return roleMenuMapper.selectById(id);
    }

    @Transactional
    public void deleteRoleMenu(Long id) {
        super.removeById(id);
    }
}
