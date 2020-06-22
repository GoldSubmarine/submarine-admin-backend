package com.htnova.system.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.converter.DtoConverter;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.system.workflow.dto.FlowLeaveDto;
import com.htnova.system.workflow.entity.FlowLeave;
import com.htnova.system.workflow.mapstruct.FlowLeaveMapStruct;
import com.htnova.system.workflow.service.FlowLeaveService;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

/** @menu 请假申请 */
@RestController
@RequestMapping("/flow-leave")
public class FlowLeaveController {

    @Resource private FlowLeaveService flowLeaveService;

    /** 分页查询 */
    @GetMapping("/list/page")
    public XPage<FlowLeaveDto> findListByPage(FlowLeaveDto flowLeaveDto, XPage<Void> xPage) {
        IPage<FlowLeave> flowLeavePage =
                flowLeaveService.findFlowLeaveList(flowLeaveDto, XPage.toIPage(xPage));
        return DtoConverter.toDto(flowLeavePage, FlowLeaveMapStruct.class);
    }

    /** 查询 */
    @GetMapping("/list/all")
    public List<FlowLeaveDto> findList(FlowLeaveDto flowLeaveDto) {
        List<FlowLeave> flowLeaveList = flowLeaveService.findFlowLeaveList(flowLeaveDto);
        return DtoConverter.toDto(flowLeaveList, FlowLeaveMapStruct.class);
    }

    /** 详情 */
    @GetMapping("/detail/{id}")
    public FlowLeaveDto getById(@PathVariable long id) {
        FlowLeave flowLeave = flowLeaveService.getFlowLeaveById(id);
        return DtoConverter.toDto(flowLeave, FlowLeaveMapStruct.class);
    }

    /** 根据processInstanceId查找详情 */
    @GetMapping("/detail/by-process-instance-id/{id}")
    public FlowLeaveDto getFlowLeaveDetailByProcessInstanceId(@PathVariable String id) {
        FlowLeave flowLeave = flowLeaveService.getDetailByProcessInstanceId(id);
        return DtoConverter.toDto(flowLeave, FlowLeaveMapStruct.class);
    }

    /** 保存 */
    @PostMapping("/save")
    public Result<Void> save(@Valid @RequestBody FlowLeaveDto flowLeaveDto) {
        flowLeaveService.saveFlowLeave(
                DtoConverter.toEntity(flowLeaveDto, FlowLeaveMapStruct.class));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 审批完成 */
    @PostMapping("/approve")
    public Result<Void> approve(@RequestBody FlowLeaveDto flowLeaveDto) {
        flowLeaveService.approve(DtoConverter.toEntity(flowLeaveDto, FlowLeaveMapStruct.class));
        return Result.build(ResultStatus.APPROVE_SUCCESS);
    }

    /** 删除 */
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable long id) {
        flowLeaveService.deleteFlowLeave(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
