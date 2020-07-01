package com.htnova.system.workflow.dto;

import cn.hutool.core.bean.BeanUtil;
import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.persistence.entity.HistoricProcessInstanceEntityImpl;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActApplyDTO {
    private String processDefinitionCategory;
    private String processInstanceBusinessKey;
    private String startedBy;
    private String processInstanceName;

    private String processInstanceId;
    private String startUserId;
    private String startActivityId;
    private String superProcessInstanceId;
    private String tenantId;
    private String name;
    private String description;
    private String processDefinitionId;
    private String processDefinitionKey;
    private String processDefinitionName;
    private Integer processDefinitionVersion;
    private String deploymentId;
    private String callbackId;
    private String callbackType;
    private String referenceId;
    private String referenceType;
    private Date startTime;
    private Date endTime;
    private String deleteReason;
    private ProcessVariableDTO processVariables;

    private ApplyStatus status;
    /** 当前节点的task id */
    private String activityId;

    public ActApplyDTO(HistoricProcessInstance hisProcessIns) {
        this.processInstanceId =
                ((HistoricProcessInstanceEntityImpl) hisProcessIns).getProcessInstanceId();
        this.processDefinitionId = hisProcessIns.getProcessDefinitionId();
        this.processInstanceBusinessKey = hisProcessIns.getBusinessKey();
        this.startUserId = hisProcessIns.getStartUserId();
        this.startActivityId = hisProcessIns.getStartActivityId();
        this.superProcessInstanceId = hisProcessIns.getSuperProcessInstanceId();
        this.tenantId = hisProcessIns.getTenantId();
        this.name = hisProcessIns.getName();
        this.description = hisProcessIns.getDescription();
        this.processDefinitionKey = hisProcessIns.getProcessDefinitionKey();
        this.processDefinitionName = hisProcessIns.getProcessDefinitionName();
        this.processDefinitionVersion = hisProcessIns.getProcessDefinitionVersion();
        this.deploymentId = hisProcessIns.getDeploymentId();
        this.callbackId = hisProcessIns.getCallbackId();
        this.callbackType = hisProcessIns.getCallbackType();
        this.referenceId = hisProcessIns.getReferenceId();
        this.referenceType = hisProcessIns.getReferenceType();
        this.startTime = hisProcessIns.getStartTime();
        this.endTime = hisProcessIns.getEndTime();
        this.deleteReason = hisProcessIns.getDeleteReason();
        this.processVariables =
                BeanUtil.mapToBean(
                        hisProcessIns.getProcessVariables(), ProcessVariableDTO.class, true);

        if (Objects.nonNull(hisProcessIns.getEndActivityId())) {
            this.status = ApplyStatus.finish;
        } else if (StringUtils.isNotBlank(hisProcessIns.getDeleteReason())) {
            this.status = ApplyStatus.delete;
        } else {
            this.status = ApplyStatus.unfinish;
        }
    }

    public enum ApplyStatus {
        /** 进行中 */
        unfinish,
        /** 已完成 */
        finish,
        /** 已撤销 */
        delete
    }
}
