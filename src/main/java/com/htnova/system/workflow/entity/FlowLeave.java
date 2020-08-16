package com.htnova.system.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.htnova.common.base.BaseEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@TableName("t_flow_leave")
public class FlowLeave extends BaseEntity {
    public static final String CODE = "FLOW_LEAVE";

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
}
