package com.htnova.system.tool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.system.tool.dto.QuartzLogDto;
import com.htnova.system.tool.entity.QuartzJob;
import com.htnova.system.tool.entity.QuartzLog;
import com.htnova.system.tool.mapper.QuartzLogMapper;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuartzLogService extends ServiceImpl<QuartzLogMapper, QuartzLog> {
    @Resource
    private QuartzLogMapper quartzLogMapper;

    @Transactional(readOnly = true)
    public IPage<QuartzLog> findQuartzLogList(QuartzLogDto quartzLogDto, IPage<Void> xPage) {
        return quartzLogMapper.findPage(xPage, quartzLogDto);
    }

    @Transactional(readOnly = true)
    public List<QuartzLog> findQuartzLogList(QuartzLogDto quartzLogDto) {
        return quartzLogMapper.findList(quartzLogDto);
    }

    @Transactional
    public void saveQuartzLog(QuartzLog quartzLog) {
        super.saveOrUpdate(quartzLog);
    }

    @Transactional
    public void saveQuartzLog(QuartzJob quartzJob, QuartzLog.StatusType status, long time, String detail) {
        this.save(
                QuartzLog
                    .builder()
                    .jobName(quartzJob.getJobName())
                    .beanName(quartzJob.getBeanName())
                    .methodName(quartzJob.getMethodName())
                    .params(quartzJob.getParams())
                    .cronExpression(quartzJob.getCronExpression())
                    .status(status)
                    .time(time)
                    .detail(detail)
                    .build()
            );
    }

    @Transactional
    public QuartzLog getQuartzLogById(long id) {
        return quartzLogMapper.selectById(id);
    }

    @Transactional
    public void deleteQuartzLog(Long id) {
        super.removeById(id);
    }
}
