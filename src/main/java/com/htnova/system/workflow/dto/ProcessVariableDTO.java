package com.htnova.system.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class ProcessVariableDTO {
    /** 申请人id */
    private String applyUserId;

    /** 申请人姓名 */
    private String applyUserName;

    /** 流程定义id */
    private String processDefinitionId;

    /** 流程定义名称 */
    private String processDefinitionName;

    /** 流程定义key */
    private String processDefinitionKey;

    /** 流程定义分类 */
    private String processDefinitionCategory;

    /** 下一个节点审批人（流程图中的变量） */
    private String approver;

    /** 审批是否通过（对应流程图中网关的判断变量，该变量属于流程实例） */
    private String status;
}
