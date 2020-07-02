package com.htnova.system.workflow.dto;

import cn.hutool.core.bean.BeanUtil;
import com.htnova.common.util.DateUtil;
import com.htnova.system.workflow.dto.TaskVariableDTO.ApproveType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActTaskDTO {

    private String id;
    private String name;
    private String description;
    private String owner;
    /** 受托人 */
    private String assigneeId;

    private String assigneeName;

    /** 委派状态 */
    private DelegationState delegationState;
    /** 审核状态 */
    private ApproveType approveStatus;

    private String category;
    private LocalDateTime dueDate;
    private String tenantId;
    /** 挂起状态 */
    private boolean suspended;

    private String executionId;
    private String taskDefinitionId;
    private String taskDefinitionKey;

    private String processInstanceId;
    private String processDefinitionId;
    private String processDefinitionKey;
    private String processDefinitionName;
    private String processDefinitionCategory;
    private ProcessVariableDTO processVariables;

    private LocalDateTime createTime;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Long durationInMillis;
    private String comment;

    /** 申请人id */
    private String applyUserId;

    /** 申请人姓名 */
    private String applyUserName;

    public ActTaskDTO(Task task) {
        this.setId(task.getId());
        this.setName(task.getName());
        this.setDescription(task.getDescription());
        this.setOwner(task.getOwner());
        this.setAssigneeId(task.getAssignee());
        this.setDelegationState(task.getDelegationState());
        this.setCategory(task.getCategory());
        this.setDueDate(DateUtil.converter(task.getDueDate()));
        this.setTenantId(task.getTenantId());
        this.setSuspended(task.isSuspended());
        this.setExecutionId(task.getExecutionId());
        this.setTaskDefinitionId(task.getTaskDefinitionId());
        this.setTaskDefinitionKey(task.getTaskDefinitionKey());
        this.setProcessInstanceId(task.getProcessInstanceId());
        this.setProcessDefinitionId(task.getProcessDefinitionId());
        this.setProcessVariables(
                BeanUtil.mapToBean(task.getProcessVariables(), ProcessVariableDTO.class, true));
        this.setProcessDefinitionKey(this.getProcessVariables().getProcessDefinitionKey());
        this.setProcessDefinitionName(this.getProcessVariables().getProcessDefinitionName());
        this.setApplyUserId(this.getProcessVariables().getApplyUserId());
        this.setApplyUserName(this.getProcessVariables().getApplyUserName());
        this.setCreateTime(DateUtil.converter(task.getCreateTime()));
    }

    public ActTaskDTO(HistoricTaskInstance historicTask) {
        this.setAssigneeId(historicTask.getAssignee());
        this.setId(historicTask.getId());
        this.setName(historicTask.getName());
        this.setEndTime(DateUtil.converter(historicTask.getEndTime()));
        this.setExecutionId(historicTask.getExecutionId());
        this.setProcessInstanceId(historicTask.getProcessInstanceId());
        this.setProcessDefinitionId(historicTask.getProcessDefinitionId());
        this.setTaskDefinitionKey(historicTask.getTaskDefinitionKey());
        this.setProcessVariables(
                BeanUtil.mapToBean(
                        historicTask.getProcessVariables(), ProcessVariableDTO.class, true));
        this.setProcessDefinitionKey(this.getProcessVariables().getProcessDefinitionKey());
        this.setProcessDefinitionName(this.getProcessVariables().getProcessDefinitionName());
        this.setApplyUserId(this.getProcessVariables().getApplyUserId());
        this.setApplyUserName(this.getProcessVariables().getApplyUserName());
    }
}
