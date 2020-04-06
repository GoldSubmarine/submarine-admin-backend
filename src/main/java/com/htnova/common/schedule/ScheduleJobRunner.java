package com.htnova.common.schedule;

import com.htnova.common.util.ScheduleUtil;
import com.htnova.system.tool.entity.QuartzJob;
import com.htnova.system.tool.service.QuartzJobService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * spring启动时初始化定时任务
 */
@Component
public class ScheduleJobRunner implements ApplicationRunner {

    @Resource
    private QuartzJobService quartzJobService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        quartzJobService
                .lambdaQuery()
                .eq(QuartzJob::getStatus, QuartzJob.StatusType.enable)
                .list()
                .forEach(ScheduleUtil::createScheduleJob);
    }
}
