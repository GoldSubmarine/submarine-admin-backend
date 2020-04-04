package com.htnova.system.tool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.common.base.BaseEntity;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.exception.ServiceException;
import com.htnova.common.util.ScheduleUtil;
import com.htnova.common.util.SpringContextUtil;
import com.htnova.system.tool.dto.QuartzJobDto;
import com.htnova.system.tool.entity.QuartzJob;
import com.htnova.system.tool.mapper.QuartzJobMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class QuartzJobService extends ServiceImpl<QuartzJobMapper, QuartzJob> {

    @Resource
    private QuartzJobMapper quartzJobMapper;

    @Transactional(readOnly = true)
    public IPage<QuartzJob> findQuartzJobList(QuartzJobDto quartzJobDto, IPage<Void> xPage) {
        return quartzJobMapper.findPage(xPage, quartzJobDto);
    }

    @Transactional(readOnly = true)
    public List<QuartzJob> findQuartzJobList(QuartzJobDto quartzJobDto) {
        return quartzJobMapper.findList(quartzJobDto);
    }

    @Transactional
    public void saveQuartzJob(QuartzJob quartzJob) {
        // 检测bean是否存在
        Object bean = SpringContextUtil.getBean(quartzJob.getBeanName());
        // 检测方法是否存在
        try {
            if(StringUtils.isNotBlank(quartzJob.getParams())) {
                bean.getClass().getMethod(quartzJob.getMethodName(), String.class);
            } else {
                bean.getClass().getMethod(quartzJob.getMethodName());
            }
        } catch (NoSuchMethodException e) {
            throw new ServiceException(ResultStatus.QUARTZ_METHOD_NOT_EXIST);
        }
        // 检测cron表达式是否正确
        if(!CronExpression.isValidExpression(quartzJob.getCronExpression())) {
            throw new ServiceException(ResultStatus.QUARTZ_EXPRESSION_INVALID);
        }
        // 如果是更新操作，先删除原有的任务
        if(Objects.nonNull(quartzJob.getId())) {
            QuartzJob origin = super.getById(quartzJob.getId());
            ScheduleUtil.deleteScheduleJob(origin.getId());
        }
        super.saveOrUpdate(quartzJob);
        ScheduleUtil.createScheduleJob(quartzJob);
    }

    @Transactional
    public QuartzJob getQuartzJobById(long id) {
        return quartzJobMapper.selectById(id);
    }

    @Transactional
    public void deleteQuartzJob(Long id) {
        ScheduleUtil.deleteScheduleJob(id);
        super.removeById(id);
    }

    @Transactional
    public void changeScheduleJobStatus(Long id, QuartzJob.StatusType status) {
        super.lambdaUpdate().eq(BaseEntity::getId, id).set(QuartzJob::getStatus, status).update();
        if(QuartzJob.StatusType.disable.equals(status)) {
            ScheduleUtil.deleteScheduleJob(id);
        } else {
            ScheduleUtil.createScheduleJob(super.getById(id));
        }
    }

    @Transactional
    public void runScheduleJob(Long id) {
        ScheduleUtil.runOnce(super.getById(id));
    }

    /**
     * 定时任务多参数示例
     */
    public void testSchedule(String params) {
        String[] paramArr = params.split(",");
        log.info("定时任务示例，参数：{}", Arrays.asList(paramArr));
    }

}
