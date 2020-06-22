package com.htnova.system.workflow.dto;

import cn.hutool.core.bean.BeanUtil;
import java.util.Date;
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

    private String category;
    private Date dueDate;
    private String tenantId;
    private String formKey;
    /** 挂起状态 */
    private boolean suspended;

    private String executionId;
    private String taskDefinitionId;
    private String taskDefinitionKey;

    private String processInstanceId;
    private String processDefinitionId;
    private String processDefinitionKey;
    private ProcessVariableDTO processVariables;

    private Date createTime;
    private Date beginTime;
    private Date endTime;
    private Long durationInMillis;
    private String comment;

    private String startActivityId;
    /** 当前审批节点id */
    private String activityId;

    private String activityType;

    public ActTaskDTO(Task task) {
        this.setId(task.getId());
        this.setName(task.getName());
        this.setDescription(task.getDescription());
        this.setOwner(task.getOwner());
        this.setAssigneeId(task.getAssignee());
        this.setDelegationState(task.getDelegationState());
        this.setCategory(task.getCategory());
        this.setDueDate(task.getDueDate());
        this.setTenantId(task.getTenantId());
        this.setFormKey(task.getFormKey());
        this.setSuspended(task.isSuspended());
        this.setExecutionId(task.getExecutionId());
        this.setTaskDefinitionId(task.getTaskDefinitionId());
        this.setTaskDefinitionKey(task.getTaskDefinitionKey());
        this.setProcessInstanceId(task.getProcessInstanceId());
        this.setProcessDefinitionId(task.getProcessDefinitionId());
        this.setProcessVariables(
                BeanUtil.mapToBean(task.getProcessVariables(), ProcessVariableDTO.class, true));
        this.setCreateTime(task.getCreateTime());
    }

    public ActTaskDTO(HistoricTaskInstance historicTask) {
        this.setAssigneeId(historicTask.getAssignee());
        this.setId(historicTask.getId());
        this.setName(historicTask.getName());
        this.setEndTime(historicTask.getEndTime());
        this.setExecutionId(historicTask.getExecutionId());
        this.setProcessInstanceId(historicTask.getProcessInstanceId());
        this.setProcessDefinitionId(historicTask.getProcessDefinitionId());
        this.setTaskDefinitionKey(historicTask.getTaskDefinitionKey());
        this.setProcessVariables(
                BeanUtil.mapToBean(
                        historicTask.getProcessVariables(), ProcessVariableDTO.class, true));
    }
}
