package com.htnova.system.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.common.dto.XPageImpl;
import com.htnova.common.util.DateUtil;
import com.htnova.system.workflow.dto.ActProcessDTO;
import com.htnova.system.workflow.service.ActProcessService;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.flowable.common.engine.api.repository.EngineDeployment;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.web.bind.annotation.*;

// dinghuang.github.io/2020/03/14/Activiti7.X结合SpringBoot2.1、Mybatis/
// destinywang.github.io/blog/2018/12/11/Activiti-3-数据模型设计/
@RestController
@RequestMapping("/workflow/process")
public class ActProcessController {

    @Resource private ActProcessService actProcessService;
    @Resource private RepositoryService repositoryService;

    /** 分页查询 */
    //    @PreAuthorize("hasAnyAuthority('fileStore.find')")
    @GetMapping("/list/page")
    public XPage<ActProcessDTO> findListByPage(
            ActProcessDTO actProcessDTO, XPage<ActProcessDTO> xPage) {
        IPage<ProcessDefinition> iPage =
                actProcessService.findActProcessList(actProcessDTO, XPage.toIPage(xPage));
        Map<String, LocalDateTime> deployTimeMap =
                repositoryService.createDeploymentQuery()
                        .deploymentIds(
                                iPage.getRecords().stream()
                                        .map(ProcessDefinition::getDeploymentId)
                                        .collect(Collectors.toList()))
                        .list().stream()
                        .collect(
                                Collectors.toMap(
                                        EngineDeployment::getId,
                                        item -> DateUtil.converter(item.getDeploymentTime()),
                                        (a, b) -> a));
        XPageImpl<ActProcessDTO> result = new XPageImpl<>();
        result.setTotal(iPage.getTotal());
        result.setPageSize(iPage.getSize());
        result.setPageNum(iPage.getCurrent());
        result.setOrders(iPage.orders());
        result.setData(
                iPage.getRecords().stream()
                        .map(
                                item ->
                                        new ActProcessDTO(
                                                item, deployTimeMap.get(item.getDeploymentId())))
                        .collect(Collectors.toList()));
        return result;
    }

    /** 详情 */
    @GetMapping("/detail/{id}")
    public ActProcessDTO getById(@PathVariable String id) {
        return actProcessService.getActProcessById(id);
    }

    /** 获取资源 */
    @GetMapping("/resource/{id}")
    public String getProcessResource(@PathVariable String id, ActProcessDTO actProcessDTO) {
        return actProcessService.getActProcessResource(id, actProcessDTO.getName());
    }

    /** 激活/挂起 */
    @PostMapping("/status/{id}")
    public Result<Void> changeProcessStatus(
            @PathVariable String id, @RequestBody ActProcessDTO actProcessDTO) {
        actProcessService.changeProcessStatus(id, actProcessDTO.getSuspensionState());
        return Result.build(ResultStatus.STATUS_SET_SUCCESS);
    }

    /** 删除部署及实例 */
    @DeleteMapping("/del/{deploymentId}")
    public Result<Void> delete(@PathVariable String deploymentId) {
        actProcessService.deleteActDeployment(deploymentId);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
