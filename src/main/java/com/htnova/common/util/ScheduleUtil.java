package com.htnova.common.util;

import com.htnova.common.constant.ResultStatus;
import com.htnova.common.exception.ServiceException;
import com.htnova.common.schedule.ScheduleJob;
import com.htnova.system.tool.entity.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

@Slf4j
public class ScheduleUtil {

    private ScheduleUtil() {}

    private static final String JOB_NAME = "TASK_";

    /**
     * 创建定时任务 定时任务创建之后默认启动状态 目前最多只支持一个 String 参数
     *
     * @param quartzJob 定时任务信息类
     */
    public static void createScheduleJob(QuartzJob quartzJob) {
        Scheduler scheduler = SpringContextUtil.getBean(Scheduler.class);
        try {
            JobDetail jobDetail = JobBuilder
                .newJob(ScheduleJob.class)
                .withIdentity(JOB_NAME + quartzJob.getId())
                .build();
            jobDetail.getJobDataMap().put(ScheduleJob.QUARTZ_JOB_KEY, quartzJob);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression());
            CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(JOB_NAME + quartzJob.getId())
                .withSchedule(scheduleBuilder)
                .build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new ServiceException(ResultStatus.QUARTZ_CREATE_FAIL);
        }
    }

    /** 根据任务 ID 暂停定时任务 */
    public static void pauseScheduleJob(long jobId) {
        Scheduler scheduler = SpringContextUtil.getBean(Scheduler.class);
        JobKey jobKey = JobKey.jobKey(JOB_NAME + jobId);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new ServiceException(ResultStatus.QUARTZ_PAUSE_FAIL);
        }
    }

    /** 根据任务 ID 恢复定时任务 */
    public static void resumeScheduleJob(long jobId) {
        Scheduler scheduler = SpringContextUtil.getBean(Scheduler.class);
        JobKey jobKey = JobKey.jobKey(JOB_NAME + jobId);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new ServiceException(ResultStatus.QUARTZ_RESUME_FAIL);
        }
    }

    /** 根据任务 ID 立即运行一次定时任务 */
    public static void runOnce(long jobId) {
        Scheduler scheduler = SpringContextUtil.getBean(Scheduler.class);
        JobKey jobKey = JobKey.jobKey(JOB_NAME + jobId);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            throw new ServiceException(ResultStatus.QUARTZ_RUN_FAIL);
        }
    }

    /** 根据任务 QuartzJob 立即运行一次定时任务 */
    public static void runOnce(QuartzJob quartzJob) {
        Scheduler scheduler = SpringContextUtil.getBean(Scheduler.class);
        JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(JOB_NAME + quartzJob.getId()).build();
        jobDetail.getJobDataMap().put(ScheduleJob.QUARTZ_JOB_KEY, quartzJob);
        SimpleTrigger trigger = TriggerBuilder
            .newTrigger()
            .withIdentity(JOB_NAME + quartzJob.getId())
            .withSchedule(SimpleScheduleBuilder.simpleSchedule())
            .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new ServiceException(ResultStatus.QUARTZ_RUN_FAIL);
        }
    }

    /** 更新 cron 表达式 */
    public static void updateScheduleJob(QuartzJob quartzJob) {
        Scheduler scheduler = SpringContextUtil.getBean(Scheduler.class);
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + quartzJob.getId());
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            // 重置对应的job
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            throw new ServiceException(ResultStatus.QUARTZ_EXPRESSION_INVALID);
        }
    }

    /** 根据定时任务名称从调度器当中删除定时任务 */
    public static void deleteScheduleJob(long jobId) {
        Scheduler scheduler = SpringContextUtil.getBean(Scheduler.class);
        JobKey jobKey = JobKey.jobKey(JOB_NAME + jobId);
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new ServiceException(ResultStatus.QUARTZ_DELETE_FAIL);
        }
    }
}
