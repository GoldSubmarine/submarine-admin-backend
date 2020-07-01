package com.htnova.system.workflow.controller;

import com.htnova.common.base.BaseEntity;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.common.util.UserUtil;
import com.htnova.system.workflow.dto.ActApplyDTO;
import com.htnova.system.workflow.dto.ActTaskDTO;
import com.htnova.system.workflow.service.ActTaskService;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workflow/task")
public class ActTaskController {

    @Resource private ActTaskService actTaskService;

    /** 获取待完成列表 */
    @GetMapping("/todo/page")
    public XPage<ActTaskDTO> getTodoList(ActTaskDTO actTaskDTO, XPage<ActTaskDTO> xPage) {
        return actTaskService.getTodoList(
                actTaskDTO,
                UserUtil.getAuthUser().getId(),
                UserUtil.getAuthUser().getRoleList().stream()
                        .map(BaseEntity::getId)
                        .collect(Collectors.toList()),
                xPage);
    }

    /** 获取已完成列表 */
    @GetMapping("/done/page")
    public XPage<ActTaskDTO> getDonePage(
            ActTaskDTO actTaskDTO, String userId, XPage<ActTaskDTO> page) {
        return actTaskService.getDonePage(
                actTaskDTO, UserUtil.getAuthUser().getId().toString(), page);
    }

    /** 获取我的申请 */
    @GetMapping("/apply/page")
    public XPage<ActApplyDTO> getApplyPage(ActApplyDTO actApplyDTO, XPage<ActApplyDTO> page) {
        actApplyDTO.setStartedBy(UserUtil.getAuthUser().getId().toString());
        return actTaskService.getApplyPage(actApplyDTO, page);
    }

    /** 签收任务 */
    @PostMapping("/apply/claim/{taskId}")
    public Result<Void> claimTask(@PathVariable String taskId) {
        actTaskService.claimTask(taskId, UserUtil.getAuthUser().getId());
        return Result.build(ResultStatus.CLAIM_SUCCESS);
    }

    /** 撤销申请 */
    @PostMapping("/apply/delete")
    public Result<Void> deleteProcessInstanceById(@RequestBody ActApplyDTO actApplyDTO) {
        actTaskService.deleteProcessInstanceById(
                actApplyDTO.getProcessInstanceId(), actApplyDTO.getDeleteReason());
        return Result.build(ResultStatus.REVOKE_SUCCESS);
    }

    /** 获取历史操作记录 */
    @GetMapping("/history/operate")
    public List<ActTaskDTO> getHistoryOperate(ActTaskDTO actTaskDTO) {
        return actTaskService.histoicFlowList(
                actTaskDTO.getProcessInstanceId(), actTaskDTO.getTaskDefinitionKey());
    }
}
