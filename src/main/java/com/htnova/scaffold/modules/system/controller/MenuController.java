package com.htnova.scaffold.modules.system.controller;

import com.htnova.scaffold.common.dto.XPage;
import com.htnova.scaffold.common.util.CommonUtil;
import com.htnova.scaffold.modules.system.dto.MenuDto;
import com.htnova.scaffold.modules.system.entity.Menu;
import com.htnova.scaffold.modules.system.mapstruct.MenuMapStruct;
import com.htnova.scaffold.modules.system.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @menu 菜单
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 菜单分页查询
     */
    @GetMapping("/list/page")
    public XPage<MenuDto> findListByPage(MenuDto menuDto, XPage xPage) {
       XPage<Menu> menuPage = menuService.findMenuList(MenuDto.toEntity(menuDto), xPage);
        return menuPage.toDto(MenuMapStruct.class);
    }

    /**
     * 菜单查询
     */
    @GetMapping("/list/all")
    public List<MenuDto> findList(MenuDto menuDto) {
        List<Menu> menuList = menuService.findMenuList(MenuDto.toEntity(menuDto));
        return CommonUtil.toDto(menuList, MenuMapStruct.class);
    }

    /**
     * 菜单详情
     */
    @GetMapping("/detail")
    public MenuDto getById(Long id) {
        Menu menu = menuService.getMenuById(id);
        return MenuDto.toDto(menu);
    }

    /**
     * 获取菜单的tree
     * @return List<MenuDto>: 返回值为list，可能有多个root节点
     */
    @GetMapping("/tree/list")
    public List<MenuDto> getMenuTree(MenuDto menuDto) {
        List<Menu> menuList = menuService.findMenuList(MenuDto.toEntity(menuDto));
        List<Menu> treeList = CommonUtil.listToTree(menuList);
        return CommonUtil.toDto(treeList, MenuMapStruct.class);
    }

    /**
     * 菜单保存
     */
    @PostMapping("/save")
    public void save(MenuDto menuDto) {
        menuService.saveMenu(MenuDto.toEntity(menuDto));
    }

    /**
     * 菜单删除
     */
    @DeleteMapping("/del")
    public void delete(MenuDto menuDto) {
        menuService.deleteMenu(menuDto.getId());
    }
}
