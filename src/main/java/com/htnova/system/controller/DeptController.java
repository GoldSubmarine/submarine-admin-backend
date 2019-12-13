package com.htnova.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.converter.DtoConverter;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.common.converter.TreeConverter;
import com.htnova.system.dto.DeptDto;
import com.htnova.system.entity.Dept;
import com.htnova.system.mapstruct.DeptMapStruct;
import com.htnova.system.service.DeptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @menu 部门
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    /**
     * 部门分页查询
     */
    @GetMapping("/list/page")
    public XPage<DeptDto> findListByPage(DeptDto deptDto, XPage<Void> xPage) {
        IPage<Dept> deptPage = deptService.findDeptList(deptDto, xPage);
        return DtoConverter.toDto(deptPage, DeptMapStruct.class);
    }

    /**
     * 部门查询
     */
    @GetMapping("/list/all")
    public List<DeptDto> findList(DeptDto deptDto) {
        List<Dept> deptList = deptService.findDeptList(deptDto);
        return DtoConverter.toDto(deptList, DeptMapStruct.class);
    }

    /**
     * 部门详情
     */
    @GetMapping("/detail")
    public DeptDto getById(Long id) {
        Dept dept = deptService.getDeptById(id);
        return DtoConverter.toDto(dept, DeptMapStruct.class);
    }

    /**
     * 获取部门的tree
     * @return List<DeptDto>: 返回值为list，可能有多个root节点
     */
    @GetMapping("/tree/list")
    public List<DeptDto> getDeptTree(DeptDto deptDto) {
        List<Dept> deptList = deptService.findDeptList(deptDto);
        List<Dept> treeList = TreeConverter.listToTree(deptList);
        return DtoConverter.toDto(treeList, DeptMapStruct.class);
    }

    /**
     * 部门保存
     */
    @PostMapping("/save")
    public Result<Void> save(@RequestBody DeptDto deptDto) {
        deptService.saveDept(DtoConverter.toEntity(deptDto, DeptMapStruct.class));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /**
     * 部门删除
     */
    @DeleteMapping("/del")
    public Result<Void> delete(@RequestBody DeptDto deptDto) {
        deptService.deleteDept(deptDto.getId());
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
