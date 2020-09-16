package com.htnova.system.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.converter.DtoConverter;
import com.htnova.common.converter.TreeConverter;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.system.manage.dto.DeptDto;
import com.htnova.system.manage.entity.Dept;
import com.htnova.system.manage.mapstruct.DeptMapStruct;
import com.htnova.system.manage.service.DeptService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** @menu 部门 */
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Resource
    private DeptService deptService;

    /** 部门分页查询 */
    @PreAuthorize("hasAnyAuthority('dept.find')")
    @GetMapping("/list/page")
    public XPage<DeptDto> findListByPage(DeptDto deptDto, XPage<Void> xPage) {
        IPage<Dept> deptPage = deptService.findDeptList(deptDto, XPage.toIPage(xPage));
        return DtoConverter.toDto(deptPage, DeptMapStruct.INSTANCE);
    }

    /** 部门查询 */
    @PreAuthorize("hasAnyAuthority('dept.find')")
    @GetMapping("/list/all")
    public List<DeptDto> findList(DeptDto deptDto) {
        List<Dept> deptList = deptService.findDeptList(deptDto);
        return DtoConverter.toDto(deptList, DeptMapStruct.INSTANCE);
    }

    /** 部门详情 */
    @PreAuthorize("hasAnyAuthority('dept.find')")
    @GetMapping("/detail/{id}")
    public DeptDto getById(@PathVariable long id) {
        Dept dept = deptService.getDeptById(id);
        return DtoConverter.toDto(dept, DeptMapStruct.INSTANCE);
    }

    /**
     * 获取部门的tree
     *
     * @return List<DeptDto>: 返回值为list，可能有多个root节点
     */
    @PreAuthorize("hasAnyAuthority('dept.find')")
    @GetMapping("/tree/list")
    public List<DeptDto> getDeptTree(DeptDto deptDto) {
        List<Dept> deptList = deptService.findDeptList(deptDto);
        List<Dept> treeList = TreeConverter.listToTree(deptList);
        return DtoConverter.toDto(treeList, DeptMapStruct.INSTANCE);
    }

    /** 部门保存 */
    @PreAuthorize("hasAnyAuthority('dept.add', 'dept.edit')")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody DeptDto deptDto) {
        deptService.saveDept(DtoConverter.toEntity(deptDto, DeptMapStruct.INSTANCE));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 部门删除 */
    @PreAuthorize("hasAnyAuthority('dept.del')")
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable long id) {
        deptService.deleteDept(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
