package com.htnova.system.tool.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.htnova.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@TableName("t_sys_quartz_log")
public class QuartzLog extends BaseEntity {
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

    /** 状态 */
    private StatusType status;

    /** 耗时 */
    private Long time;

    /** 错误详情 */
    private String detail;

    public enum StatusType {
        success,
        fail,
    }
}
