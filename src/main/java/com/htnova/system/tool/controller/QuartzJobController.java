package com.htnova.system.tool.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.converter.DtoConverter;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.system.tool.dto.QuartzJobDto;
import com.htnova.system.tool.dto.QuartzLogDto;
import com.htnova.system.tool.entity.QuartzJob;
import com.htnova.system.tool.entity.QuartzLog;
import com.htnova.system.tool.mapstruct.QuartzJobMapStruct;
import com.htnova.system.tool.mapstruct.QuartzLogMapStruct;
import com.htnova.system.tool.service.QuartzJobService;
import com.htnova.system.tool.service.QuartzLogService;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** @menu 定时任务 */
@RestController
@RequestMapping("/quartz-job")
public class QuartzJobController {
    @Resource
    private QuartzJobService quartzJobService;

    @Resource
    private QuartzLogService quartzLogService;

    /** 分页查询 */
    @PreAuthorize("hasAnyAuthority('quartzJob.find')")
    @GetMapping("/list/page")
    public XPage<QuartzJobDto> findListByPage(QuartzJobDto quartzJobDto, XPage<Void> xPage) {
        IPage<QuartzJob> quartzJobPage = quartzJobService.findQuartzJobList(quartzJobDto, XPage.toIPage(xPage));
        return DtoConverter.toDto(quartzJobPage, QuartzJobMapStruct.INSTANCE);
    }

    /** 查询 */
    @PreAuthorize("hasAnyAuthority('quartzJob.find')")
    @GetMapping("/list/all")
    public List<QuartzJobDto> findList(QuartzJobDto quartzJobDto) {
        List<QuartzJob> quartzJobList = quartzJobService.findQuartzJobList(quartzJobDto);
        return DtoConverter.toDto(quartzJobList, QuartzJobMapStruct.INSTANCE);
    }

    /** 详情 */
    @PreAuthorize("hasAnyAuthority('quartzJob.find')")
    @GetMapping("/detail/{id}")
    public QuartzJobDto getById(@PathVariable long id) {
        QuartzJob quartzJob = quartzJobService.getQuartzJobById(id);
        return DtoConverter.toDto(quartzJob, QuartzJobMapStruct.INSTANCE);
    }

    /** 保存 */
    @PreAuthorize("hasAnyAuthority('quartzJob.add', 'quartzJob.edit')")
    @PostMapping("/save")
    public Result<Void> save(@Valid @RequestBody QuartzJobDto quartzJobDto) {
        quartzJobService.saveQuartzJob(DtoConverter.toEntity(quartzJobDto, QuartzJobMapStruct.INSTANCE));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 删除 */
    @PreAuthorize("hasAnyAuthority('quartzJob.del')")
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable long id) {
        quartzJobService.deleteQuartzJob(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }

    /** 改变任务状态 */
    @PreAuthorize("hasAnyAuthority('quartzJob.add')")
    @PostMapping("/status")
    public Result<Void> changeScheduleJobStatus(@RequestBody QuartzJobDto quartzJobDto) {
        quartzJobService.changeScheduleJobStatus(quartzJobDto.getId(), quartzJobDto.getStatus());
        return Result.build(ResultStatus.QUARTZ_PAUSE_SUCCESS);
    }

    /** 执行一次任务 */
    @PreAuthorize("hasAnyAuthority('quartzJob.add')")
    @PostMapping("/run")
    public Result<Void> runScheduleJob(@RequestBody QuartzJobDto quartzJobDto) {
        quartzJobService.runScheduleJob(quartzJobDto.getId());
        return Result.build(ResultStatus.QUARTZ_RUN_SUCCESS);
    }

    /** 查看日志 */
    @PreAuthorize("hasAnyAuthority('quartzJob.find')")
    @GetMapping("/log/page")
    public XPage<QuartzLogDto> findLogByPage(QuartzLogDto quartzLogDto, XPage<Void> xPage) {
        IPage<QuartzLog> quartzLogPage = quartzLogService.findQuartzLogList(quartzLogDto, XPage.toIPage(xPage));
        return DtoConverter.toDto(quartzLogPage, QuartzLogMapStruct.INSTANCE);
    }
}
