package com.htnova.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.common.util.CommonUtil;
import com.htnova.system.dto.RoleDto;
import com.htnova.system.dto.RolePermissionDto;
import com.htnova.system.entity.Role;
import com.htnova.system.mapstruct.RoleMapStruct;
import com.htnova.system.service.RolePermissionService;
import com.htnova.system.service.RoleService;
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
        IPage<Role> rolePage = roleService.findRoleList(RoleDto.toEntity(roleDto), xPage);
        return XPage.toDto(rolePage, RoleMapStruct.class);
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
    public Result<Void> save(@RequestBody RoleDto roleDto) {
        roleService.saveRole(RoleDto.toEntity(roleDto));
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
    @DeleteMapping("/del")
    public Result<Void> delete(@RequestBody RoleDto roleDto) {
        roleService.deleteRole(roleDto.getId());
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
