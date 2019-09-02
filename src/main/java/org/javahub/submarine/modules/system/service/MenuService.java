package org.javahub.submarine.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javahub.submarine.base.BaseEntity;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.modules.system.entity.Menu;
import org.javahub.submarine.modules.system.entity.RoleMenu;
import org.javahub.submarine.modules.system.mapper.MenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuService extends ServiceImpl<MenuMapper, Menu> {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMenuService roleMenuService;

    @Transactional(readOnly = true)
    public XPage<Menu> findMenuList(Menu menu, XPage xPage) {
        XPage<Menu> menuXPage = menuMapper.findPage(xPage, menu);
        return menuXPage;
    }

    @Transactional(readOnly = true)
    public List<Menu> findMenuList(Menu menu) {
        return menuMapper.findList(menu);
    }

    @Transactional
    public void saveMenu(Menu menu) {
        Menu parent = super.getById(menu.getPid());
        if(parent != null) {
            menu.setPids(String.format("%s%s,", Optional.ofNullable(parent.getPids()).orElse(""), parent.getId()));
        }
        super.saveOrUpdate(menu);
    }

    @Transactional
    public Menu getMenuById(Long id) {
        return menuMapper.selectById(id);
    }

    @Transactional
    public void deleteMenu(Long id) {
        // 删除角色的关联表
        roleMenuService.remove(new LambdaQueryWrapper<>(new RoleMenu()).eq(RoleMenu::getMenuId, id));
        // 删除子级和自身
        List<Menu> menuList = super.lambdaQuery().like(id != null, Menu::getPids, id).list();
        List<Long> ids = menuList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        ids.add(id);
        super.removeByIds(ids);
    }
}
