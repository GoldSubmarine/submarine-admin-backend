package com.htnova.system.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.converter.DtoConverter;
import com.htnova.common.converter.TreeConverter;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.system.manage.dto.PermissionDto;
import com.htnova.system.manage.entity.Permission;
import com.htnova.system.manage.mapstruct.PermissionMapStruct;
import com.htnova.system.manage.service.PermissionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** @menu 权限 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    /** 权限分页查询 */
    @PreAuthorize("hasAnyAuthority('permission.find')")
    @GetMapping("/list/page")
    public XPage<PermissionDto> findListByPage(PermissionDto permissionDto, XPage<Void> xPage) {
        IPage<Permission> permissionPage = permissionService.findPermissionList(permissionDto, XPage.toIPage(xPage));
        return DtoConverter.toDto(permissionPage, PermissionMapStruct.INSTANCE);
    }

    /** 权限查询 */
    @PreAuthorize("hasAnyAuthority('permission.find')")
    @GetMapping("/list/all")
    public List<PermissionDto> findList(PermissionDto permissionDto) {
        List<Permission> permissionList = permissionService.findPermissionList(permissionDto);
        return DtoConverter.toDto(permissionList, PermissionMapStruct.INSTANCE);
    }

    /** 权限详情 */
    @PreAuthorize("hasAnyAuthority('permission.find')")
    @GetMapping("/detail/{id}")
    public PermissionDto getById(@PathVariable long id) {
        Permission permission = permissionService.getPermissionById(id);
        return DtoConverter.toDto(permission, PermissionMapStruct.INSTANCE);
    }

    /**
     * 获取权限的tree
     *
     * @return List<PermissionDto>: 返回值为list，可能有多个root节点
     */
    @PreAuthorize("hasAnyAuthority('permission.find')")
    @GetMapping("/tree/list")
    public List<PermissionDto> getPermissionTree(PermissionDto permissionDto) {
        List<Permission> permissionList = permissionService.findPermissionList(permissionDto);
        List<Permission> treeList = TreeConverter.listToTree(permissionList);
        return DtoConverter.toDto(treeList, PermissionMapStruct.INSTANCE);
    }

    /** 权限保存 */
    @PreAuthorize("hasAnyAuthority('permission.add', 'permission.edit')")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody PermissionDto permissionDto) {
        permissionService.savePermission(DtoConverter.toEntity(permissionDto, PermissionMapStruct.INSTANCE));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 模块权限保存 */
    @PreAuthorize("hasAnyAuthority('permission.add', 'permission.edit')")
    @PostMapping("/save/module")
    public Result<Void> saveModule(@RequestBody PermissionDto permissionDto) {
        Permission permission = DtoConverter.toEntity(permissionDto, PermissionMapStruct.INSTANCE);
        permissionService.savePermission(permission);
        Map<String, String> map = new HashMap<>();
        map.put("查询", ".find");
        map.put("新增", ".add");
        map.put("编辑", ".edit");
        map.put("删除", ".del");
        map.forEach(
            (name, value) -> {
                permissionService.savePermission(
                    Permission
                        .builder()
                        .type(Permission.PermissionType.button)
                        .name(name)
                        .value(permission.getValue() + value)
                        .pid(permission.getId())
                        .build()
                );
            }
        );
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 权限删除 */
    @PreAuthorize("hasAnyAuthority('permission.del')")
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable long id) {
        permissionService.deletePermission(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
