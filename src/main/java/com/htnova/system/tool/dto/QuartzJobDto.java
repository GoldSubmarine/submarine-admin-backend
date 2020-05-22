package com.htnova.system.tool.dto;

import com.htnova.common.base.BaseEntity;
import com.htnova.system.tool.entity.QuartzJob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class QuartzJobDto extends BaseEntity {
    /** 任务名称 */
    private String jobName;

    /** bean名称 */
    private String beanName;

    /** 方法名称 */
    private String methodName;

    /** 参数 */
    private String params;

    /** cron表达式 */
    private String cronExpression;

    /** 是否启用 */
    private QuartzJob.StatusType status;
}
