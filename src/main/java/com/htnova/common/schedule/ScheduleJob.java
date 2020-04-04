package com.htnova.common.schedule;

import com.htnova.common.constant.ResultStatus;
import com.htnova.common.exception.ServiceException;
import com.htnova.common.util.SpringContextUtil;
import com.htnova.system.tool.entity.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 因为 quartz 库只能运行继承了 Job 接口的类，所以每个定时任务都要建一个类并继承 Job
 * 但是我们想更开放灵活一点，比如能定时运行某个 Service 方法，或多个定时任务写在一个类里而非每个定时任务都新建一个类
 * 所以设计成每次都是运行 ScheduleJob 类，把实际要运行的 QuartzJob 当作参数传给 ScheduleJob 类，
 * 然后在 ScheduleJob 内通过反射执行**实际待运行的方法**
 *
 * 注意：目前最多只支持一个 String 入参，多个入参可通过逗号分隔自行处理，参见示例: QuartzJobService.testSchedule
 */
@Slf4j
@Component
public class ScheduleJob extends QuartzJobBean {

    public static final String QUARTZ_JOB_KEY = "QUARTZ_JOB";

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        QuartzJob quartzJob = (QuartzJob) context.getJobDetail().getJobDataMap().get(QUARTZ_JOB_KEY);
        try {
            Object bean = SpringContextUtil.getBean(quartzJob.getBeanName());
            if (StringUtils.isNotBlank(quartzJob.getParams())) {
                Method method = bean.getClass().getDeclaredMethod(quartzJob.getMethodName(), String.class);
                ReflectionUtils.makeAccessible(method);
                method.invoke(bean, quartzJob.getParams());
            } else {
                Method method = bean.getClass().getDeclaredMethod(quartzJob.getMethodName());
                ReflectionUtils.makeAccessible(method);
                method.invoke(bean);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ServiceException(ResultStatus.QUARTZ_RUN_FAIL);
        }
    }
}
