package com.htnova.system.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.converter.DtoConverter;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.system.workflow.dto.FlowHistoryDto;
import com.htnova.system.workflow.entity.FlowHistory;
import com.htnova.system.workflow.mapstruct.FlowHistoryMapStruct;
import com.htnova.system.workflow.service.FlowHistoryService;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** @menu 业务数据历史记录 */
@RestController
@RequestMapping("/flow-history")
public class FlowHistoryController {

    @Resource private FlowHistoryService flowHistoryService;

    /** 分页查询 */
    @PreAuthorize("hasAnyAuthority('flowHistory.find')")
    @GetMapping("/list/page")
    public XPage<FlowHistoryDto> findListByPage(FlowHistoryDto flowHistoryDto, XPage<Void> xPage) {
        IPage<FlowHistory> flowHistoryPage =
                flowHistoryService.findFlowHistoryList(flowHistoryDto, XPage.toIPage(xPage));
        return DtoConverter.toDto(flowHistoryPage, FlowHistoryMapStruct.class);
    }

    /** 查询 */
    @GetMapping("/list/all")
    public List<FlowHistoryDto> findList(FlowHistoryDto flowHistoryDto) {
        List<FlowHistory> flowHistoryList = flowHistoryService.findFlowHistoryList(flowHistoryDto);
        return DtoConverter.toDto(flowHistoryList, FlowHistoryMapStruct.class);
    }

    /** 详情 */
    @PreAuthorize("hasAnyAuthority('flowHistory.find')")
    @GetMapping("/detail/{id}")
    public FlowHistoryDto getById(@PathVariable long id) {
        FlowHistory flowHistory = flowHistoryService.getFlowHistoryById(id);
        return DtoConverter.toDto(flowHistory, FlowHistoryMapStruct.class);
    }

    /** 保存 */
    @PreAuthorize("hasAnyAuthority('flowHistory.add', 'flowHistory.edit')")
    @PostMapping("/save")
    public Result<Void> save(@Valid @RequestBody FlowHistoryDto flowHistoryDto) {
        flowHistoryService.saveFlowHistory(
                DtoConverter.toEntity(flowHistoryDto, FlowHistoryMapStruct.class));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 删除 */
    @PreAuthorize("hasAnyAuthority('flowHistory.del')")
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable long id) {
        flowHistoryService.deleteFlowHistory(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
