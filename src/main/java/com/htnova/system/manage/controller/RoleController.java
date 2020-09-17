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
import java.util.List;
import javax.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** @menu 角色 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @Resource
    private RolePermissionService rolePermissionService;

    /** 角色分页查询 */
    @PreAuthorize("hasAnyAuthority('role.find')")
    @GetMapping("/list/page")
    public XPage<RoleDto> findListByPage(RoleDto roleDto, XPage<Void> xPage) {
        IPage<Role> rolePage = roleService.findRoleList(roleDto, XPage.toIPage(xPage));
        return DtoConverter.toDto(rolePage, RoleMapStruct.INSTANCE);
    }

    /** 角色查询 */
    @PreAuthorize("hasAnyAuthority('role.find')")
    @GetMapping("/list/all")
    public List<RoleDto> findList(RoleDto roleDto) {
        List<Role> roleList = roleService.findRoleList(roleDto);
        return DtoConverter.toDto(roleList, RoleMapStruct.INSTANCE);
    }

    /** 角色详情 */
    @PreAuthorize("hasAnyAuthority('role.find')")
    @GetMapping("/detail/{id}")
    public RoleDto getById(@PathVariable long id) {
        Role role = roleService.getRoleById(id);
        return DtoConverter.toDto(role, RoleMapStruct.INSTANCE);
    }

    /** 角色保存 */
    @PreAuthorize("hasAnyAuthority('role.add', 'role.edit')")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody RoleDto roleDto) {
        roleService.saveRole(DtoConverter.toEntity(roleDto, RoleMapStruct.INSTANCE));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 角色的权限保存 */
    @PreAuthorize("hasAnyAuthority('role.add', 'role.edit')")
    @PostMapping("/permission/save")
    public Result<Void> saveRolePermission(@RequestBody RolePermissionDto rolePermissionDto) {
        rolePermissionService.saveRolePermission(rolePermissionDto.getRoleId(), rolePermissionDto.getPermissionList());
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 批量角色的权限保存 */
    @PostMapping("/permission/batch-save")
    public Result<Void> saveRolePermissionBatch(@RequestBody RolePermissionDto rolePermissionDto) {
        rolePermissionService.saveRolePermissionBatch(
            rolePermissionDto.getBatchAuthType(),
            rolePermissionDto.getRoleIds(),
            rolePermissionDto.getPermissionList()
        );
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 角色删除 */
    @PreAuthorize("hasAnyAuthority('role.del')")
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable long id) {
        roleService.deleteRole(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
