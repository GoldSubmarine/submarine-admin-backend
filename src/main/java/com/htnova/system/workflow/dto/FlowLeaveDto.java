package com.htnova.system.workflow.dto;

import com.htnova.common.base.BaseDto;
import com.htnova.system.workflow.dto.TaskVariableDTO.ApproveType;
import com.htnova.system.workflow.interfaces.ActTask;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class FlowLeaveDto extends BaseDto implements ActTask {

    /** 开始时间 */
    private LocalDateTime beginTime;

    /** 结束时间 */
    private LocalDateTime endTime;

    /** 时长 */
    private Double duration;

    /** 请假原因 */
    private String reason;

    /** 流程实例id */
    private String processInstanceId;

    /** 流程定义id */
    private String processDefinitionId;

    /** 当前节点的id */
    private String taskId;

    /** 审批建议 */
    private String comment;

    /** 审批截图（base64） */
    private String img;

    /** 审批是否通过 */
    private ApproveType status;
}
