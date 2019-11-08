package com.htnova.scaffold.modules.system.controller;

import com.htnova.scaffold.common.dto.XPage;
import com.htnova.scaffold.common.util.CommonUtil;
import com.htnova.scaffold.modules.system.dto.PermissionDto;
import com.htnova.scaffold.modules.system.entity.Permission;
import com.htnova.scaffold.modules.system.mapstruct.PermissionMapStruct;
import com.htnova.scaffold.modules.system.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @menu 权限
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 权限分页查询
     */
    @GetMapping("/list/page")
    public XPage<PermissionDto> findListByPage(PermissionDto permissionDto, XPage xPage) {
       XPage<Permission> permissionPage = permissionService.findPermissionList(PermissionDto.toEntity(permissionDto), xPage);
        return permissionPage.toDto(PermissionMapStruct.class);
    }

    /**
     * 权限查询
     */
    @GetMapping("/list/all")
    public List<PermissionDto> findList(PermissionDto permissionDto) {
        List<Permission> permissionList = permissionService.findPermissionList(PermissionDto.toEntity(permissionDto));
        return CommonUtil.toDto(permissionList, PermissionMapStruct.class);
    }

    /**
     * 权限详情
     */
    @GetMapping("/detail")
    public PermissionDto getById(Long id) {
        Permission permission = permissionService.getPermissionById(id);
        return PermissionDto.toDto(permission);
    }

    /**
     * 获取权限的tree
     * @return List<PermissionDto>: 返回值为list，可能有多个root节点
     */
    @GetMapping("/tree/list")
    public List<PermissionDto> getPermissionTree(PermissionDto permissionDto) {
        List<Permission> permissionList = permissionService.findPermissionList(PermissionDto.toEntity(permissionDto));
        List<Permission> treeList = CommonUtil.listToTree(permissionList);
        return CommonUtil.toDto(treeList, PermissionMapStruct.class);
    }

    /**
     * 权限保存
     */
    @PostMapping("/save")
    public void save(PermissionDto permissionDto) {
        permissionService.savePermission(PermissionDto.toEntity(permissionDto));
    }

    /**
     * 模块权限保存
     */
    @PostMapping("/save/module")
    public void saveModule(PermissionDto permissionDto) {
        Permission permission = PermissionDto.toEntity(permissionDto);
        permissionService.savePermission(permission);
        Map<String, String> map = new HashMap<>();
        map.put("查询", ".find");
        map.put("新增", ".add");
        map.put("编辑", ".edit");
        map.put("删除", ".del");
        map.forEach((name, value) -> {
            permissionService.savePermission(Permission.builder()
                .name(name)
                .value(permission.getValue() + value)
                .pid(permission.getId())
                .build());
        });
    }

    /**
     * 权限删除
     */
    @DeleteMapping("/del")
    public void delete(PermissionDto permissionDto) {
        permissionService.deletePermission(permissionDto.getId());
    }
}
