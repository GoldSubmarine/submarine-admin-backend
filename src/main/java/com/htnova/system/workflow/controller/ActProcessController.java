package com.htnova.system.workflow.controller;

import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.system.workflow.dto.ActProcessDTO;
import com.htnova.system.workflow.service.ActProcessService;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.flowable.engine.RepositoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflow/process")
public class ActProcessController {

    @Resource private ActProcessService actProcessService;
    @Resource private RepositoryService repositoryService;

    /** 分页查询 */
    @PreAuthorize("hasAnyAuthority('actProcess.find')")
    @GetMapping("/list/page")
    public XPage<ActProcessDTO> findListByPage(
            ActProcessDTO actProcessDTO, XPage<ActProcessDTO> xPage) {
        return actProcessService.findActProcessList(actProcessDTO, xPage);
    }

    /** 获取全部激活状态最新版流程定义 */
    @PreAuthorize("hasAnyAuthority('actProcess.find')")
    @GetMapping("/list/all")
    public List<ActProcessDTO> findAllProcessList() {
        return actProcessService.findAllActProcessList().stream()
                .map(ActProcessDTO::new)
                .collect(Collectors.toList());
    }

    /** 详情 */
    @PreAuthorize("hasAnyAuthority('actProcess.find')")
    @GetMapping("/detail/{id}")
    public ActProcessDTO getById(@PathVariable String id) {
        return actProcessService.getActProcessById(id);
    }

    /** 获取资源 */
    @PreAuthorize("hasAnyAuthority('actProcess.find')")
    @GetMapping("/resource/{id}")
    public String getProcessResource(@PathVariable String id, ActProcessDTO actProcessDTO) {
        return actProcessService.getActProcessResource(id, actProcessDTO.getName());
    }

    /** 激活/挂起 */
    @PreAuthorize("hasAnyAuthority('actProcess.edit')")
    @PostMapping("/status/{id}")
    public Result<Void> changeProcessStatus(
            @PathVariable String id, @RequestBody ActProcessDTO actProcessDTO) {
        actProcessService.changeProcessStatus(id, actProcessDTO.getSuspensionState());
        return Result.build(ResultStatus.STATUS_SET_SUCCESS);
    }

    /** 删除部署及实例 */
    @PreAuthorize("hasAnyAuthority('actProcess.del')")
    @DeleteMapping("/del/{deploymentId}")
    public Result<Void> delete(@PathVariable String deploymentId) {
        actProcessService.deleteActDeployment(deploymentId);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
