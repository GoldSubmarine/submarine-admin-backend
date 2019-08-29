package org.javahub.submarine.modules.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.RoleMenu;
import org.javahub.submarine.modules.system.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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

    @Transactional
    public void saveRoleMenu(RoleMenu roleMenu) {
        super.saveOrUpdate(roleMenu);
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
