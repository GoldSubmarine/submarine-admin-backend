package com.htnova.system.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.converter.DtoConverter;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.system.manage.dto.RoleDto;
import com.htnova.system.manage.dto.RolePermissionDto;
import com.htnova.system.manage.entity.Role;
import com.htnova.system.manage.mapstruct.RoleMapStruct;
import com.htnova.system.manage.service.RolePermissionService;
import com.htnova.system.manage.service.RoleService;
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
    public XPage<RoleDto> findListByPage(RoleDto roleDto, XPage<Void> xPage) {
        IPage<Role> rolePage = roleService.findRoleList(roleDto, xPage);
        return DtoConverter.toDto(rolePage, RoleMapStruct.class);
    }

    /**
     * 角色查询
     */
    @GetMapping("/list/all")
    public List<RoleDto> findList(RoleDto roleDto) {
        List<Role> roleList = roleService.findRoleList(roleDto);
        return DtoConverter.toDto(roleList, RoleMapStruct.class);
    }

    /**
     * 角色详情
     */
    @GetMapping("/detail/{id}")
    public RoleDto getById(@PathVariable long id) {
        Role role = roleService.getRoleById(id);
        return DtoConverter.toDto(role, RoleMapStruct.class);
    }

    /**
     * 角色保存
     */
    @PostMapping("/save")
    public Result<Void> save(@RequestBody RoleDto roleDto) {
        roleService.saveRole(DtoConverter.toEntity(roleDto, RoleMapStruct.class));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /**
     * 角色的权限保存
     */
    @PostMapping("/permission/save")
    public Result<Void> saveRolePermission(@RequestBody RolePermissionDto rolePermissionDto) {
        rolePermissionService.saveRolePermission(rolePermissionDto.getRoleId(), rolePermissionDto.getType(), rolePermissionDto.getPermissionList());
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /**
     * 角色删除
     */
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable long id) {
        roleService.deleteRole(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
