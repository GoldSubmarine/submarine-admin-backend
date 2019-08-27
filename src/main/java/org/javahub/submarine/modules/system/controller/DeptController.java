package org.javahub.submarine.modules.system.controller;

import org.javahub.submarine.common.dto.Result;
import org.javahub.submarine.common.dto.XPage;
import org.javahub.submarine.common.util.CommonUtil;
import org.javahub.submarine.modules.system.dto.DeptDto;
import org.javahub.submarine.modules.system.entity.Dept;
import org.javahub.submarine.modules.system.service.DeptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @dept system
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
    public XPage<DeptDto> findListByPage(DeptDto deptDto, XPage xPage) {
       XPage<Dept> deptPage = deptService.findDeptList(deptDto.toEntity(), xPage);
        return deptPage.toDto();
    }

    /**
     * 部门查询
     */
    @GetMapping("/list/all")
    public List<DeptDto> findList(DeptDto deptDto) {
        List<Dept> deptList = deptService.findDeptList(deptDto.toEntity());
        return CommonUtil.toDto(deptList);
    }

    /**
     * 部门详情
     */
    @GetMapping("/detail")
    public DeptDto getById(Long id) {
        Dept dept = deptService.getDeptById(id);
        return dept.toDto();
    }

    /**
     * 获取部门的tree
     * @return List<DeptDto>: 返回值为list，可能有多个root节点
     */
    @GetMapping("/tree/list")
    public List<DeptDto> getDeptTree() {
        List<Dept> deptList = deptService.findDeptList(new Dept());
        List<Dept> treeList = CommonUtil.listToTree(deptList);
        return CommonUtil.toDto(treeList);
    }

    /**
     * 部门保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody DeptDto deptDto) {
        deptService.saveDept(deptDto.toEntity());
        return Result.successMsg("保存成功");
    }

    /**
     * 部门删除
     */
    @DeleteMapping("/del")
    public Result delete(@RequestBody DeptDto deptDto) {
        deptService.deleteDept(deptDto.getId());
        return Result.successMsg("删除成功");
    }
}
