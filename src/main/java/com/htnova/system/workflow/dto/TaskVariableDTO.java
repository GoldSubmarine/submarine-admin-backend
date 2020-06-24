package com.htnova.system.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class TaskVariableDTO {

    /** 审批是否通过（对应流程图中网关的判断变量） */
    private ApproveType status;

    /** 下一个节点审批人（流程图中的变量） */
    private String approver;

    public enum ApproveType {
        /** 通过 */
        approve,
        /** 驳回 */
        reject
    }
}
