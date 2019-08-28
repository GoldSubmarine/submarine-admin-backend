package org.javahub.submarine.modules.system.controller;

import org.javahub.submarine.common.dto.Result;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.common.util.CommonUtil;
import org.javahub.submarine.modules.system.dto.MenuDto;
import org.javahub.submarine.modules.system.entity.Menu;
import org.javahub.submarine.modules.system.mapstruct.MenuMapStruct;
import org.javahub.submarine.modules.system.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @menu system
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
       XPage<Menu> menuPage = menuService.findMenuList(menuDto.toEntity(), xPage);
        return menuPage.toDto();
    }

    /**
     * 菜单查询
     */
    @GetMapping("/list/all")
    public List<MenuDto> findList(MenuDto menuDto) {
        List<Menu> menuList = menuService.findMenuList(menuDto.toEntity());
        return CommonUtil.toDto(menuList, MenuMapStruct.class);
    }

    /**
     * 菜单详情
     */
    @GetMapping("/detail")
    public MenuDto getById(Long id) {
        Menu menu = menuService.getMenuById(id);
        return menu.toDto();
    }

    /**
     * 获取菜单的tree
     * @return List<MenuDto>: 返回值为list，可能有多个root节点
     */
    @GetMapping("/tree/list")
    public List<MenuDto> getMenuTree() {
        List<Menu> menuList = menuService.findMenuList(new Menu());
        List<Menu> treeList = CommonUtil.listToTree(menuList);
        return CommonUtil.toDto(treeList, MenuMapStruct.class);
    }

    /**
     * 菜单保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody MenuDto menuDto) {
        menuService.saveMenu(menuDto.toEntity());
        return Result.successMsg("保存成功");
    }

    /**
     * 菜单删除
     */
    @DeleteMapping("/del")
    public Result delete(@RequestBody MenuDto menuDto) {
        menuService.deleteMenu(menuDto.getId());
        return Result.successMsg("删除成功");
    }
}
