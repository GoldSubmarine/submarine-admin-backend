package com.htnova.system.tool.dto;

import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.htnova.common.base.BaseEntity;
import com.htnova.system.tool.entity.QuartzLog;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class QuartzLogDto extends BaseEntity {
	/**
	* 任务名称
	*/
	private String jobName;

	/**
	* bean名称
	*/
	private String beanName;

	/**
	* 方法名称
	*/
	private String methodName;

	/**
	* 参数
	*/
	private String params;

	/**
	* cron表达式
	*/
	private String cronExpression;

	/**
	* 状态
	*/
	private QuartzLog.StatusType status;

	/**
	* 耗时
	*/
	private Long time;

	/**
	* 错误详情
	*/
	private String detail;

}
