package com.htnova.scaffold.modules.system.controller;

import com.htnova.scaffold.common.dto.XPage;
import com.htnova.scaffold.common.util.CommonUtil;
import com.htnova.scaffold.modules.system.dto.RoleDto;
import com.htnova.scaffold.modules.system.entity.Role;
import com.htnova.scaffold.modules.system.mapstruct.RoleMapStruct;
import com.htnova.scaffold.modules.system.service.RoleMenuService;
import com.htnova.scaffold.modules.system.service.RolePermissionService;
import com.htnova.scaffold.modules.system.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @menu 角色
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 角色分页查询
     */
    @GetMapping("/list/page")
    public XPage<RoleDto> findListByPage(RoleDto roleDto, XPage<RoleDto> xPage) {
        XPage<Role> rolePage = roleService.findRoleList(RoleDto.toEntity(roleDto), xPage);
        return rolePage.toDto(RoleMapStruct.class);
    }

    /**
     * 角色查询
     */
    @GetMapping("/list/all")
    public List<RoleDto> findList(RoleDto roleDto) {
        List<Role> roleList = roleService.findRoleList(RoleDto.toEntity(roleDto));
        return CommonUtil.toDto(roleList, RoleMapStruct.class);
    }

    /**
     * 角色详情
     */
    @GetMapping("/detail")
    public RoleDto getById(long id) {
        Role role = roleService.getRoleById(id);
        return RoleDto.toDto(role);
    }

    /**
     * 角色保存
     */
    @PostMapping("/save")
    public void save(RoleDto roleDto) {
        roleService.saveRole(RoleDto.toEntity(roleDto));
    }

    /**
     * 角色的权限保存
     */
    @PostMapping("/permission/save")
    public void saveRolePermission(long id, @RequestParam(value = "permissionList") List<Long> permissionList) {
        rolePermissionService.saveRolePermission(id, permissionList);
    }

    /**
     * 角色的菜单保存
     */
    @PostMapping("/menu/save")
    public void saveRoleMenu(long id, @RequestParam(value = "menuList") List<Long> menuList) {
        roleMenuService.saveRoleMenu(id, menuList);
    }

    /**
     * 角色删除
     */
    @DeleteMapping("/del")
    public void delete(RoleDto roleDto) {
        roleService.deleteRole(roleDto.getId());
    }
}
