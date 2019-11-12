package com.htnova.modules.system.controller;

import com.htnova.common.dto.XPage;
import com.htnova.common.util.CommonUtil;
import com.htnova.modules.system.dto.RoleDto;
import com.htnova.modules.system.entity.Permission;
import com.htnova.modules.system.entity.Role;
import com.htnova.modules.system.mapstruct.RoleMapStruct;
import com.htnova.modules.system.service.RolePermissionService;
import com.htnova.modules.system.service.RoleService;
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
    public void saveRolePermission(long id, Permission.PermissionType type, @RequestParam(value = "permissionList") List<Long> permissionList) {
        rolePermissionService.saveRolePermission(id, type, permissionList);
    }

    /**
     * 角色删除
     */
    @DeleteMapping("/del")
    public void delete(RoleDto roleDto) {
        roleService.deleteRole(roleDto.getId());
    }
}
