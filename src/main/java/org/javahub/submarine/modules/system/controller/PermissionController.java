package org.javahub.submarine.modules.system.controller;

import org.javahub.submarine.common.dto.Result;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.common.util.CommonUtil;
import org.javahub.submarine.modules.system.dto.PermissionDto;
import org.javahub.submarine.modules.system.entity.Permission;
import org.javahub.submarine.modules.system.mapstruct.PermissionMapStruct;
import org.javahub.submarine.modules.system.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    public List<PermissionDto> getPermissionTree() {
        List<Permission> permissionList = permissionService.findPermissionList(new Permission());
        List<Permission> treeList = CommonUtil.listToTree(permissionList);
        return CommonUtil.toDto(treeList, PermissionMapStruct.class);
    }

    /**
     * 权限保存
     */
    @PostMapping("/save")
    public Result save(PermissionDto permissionDto) {
        permissionService.savePermission(PermissionDto.toEntity(permissionDto));
        return Result.successMsg("保存成功");
    }

    /**
     * 权限删除
     */
    @DeleteMapping("/del")
    public Result delete(PermissionDto permissionDto) {
        permissionService.deletePermission(permissionDto.getId());
        return Result.successMsg("删除成功");
    }
}
