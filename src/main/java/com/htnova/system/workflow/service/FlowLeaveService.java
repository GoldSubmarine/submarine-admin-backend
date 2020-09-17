package com.htnova.system.workflow.service;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.converter.DtoConverter;
import com.htnova.common.util.UserUtil;
import com.htnova.system.workflow.dto.FlowLeaveDto;
import com.htnova.system.workflow.entity.FlowHistory;
import com.htnova.system.workflow.entity.FlowLeave;
import com.htnova.system.workflow.mapper.FlowLeaveMapper;
import com.htnova.system.workflow.mapstruct.FlowLeaveMapStruct;
import java.util.List;
import javax.annotation.Resource;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowLeaveService extends ActBaseService<FlowLeaveMapper, FlowLeave> {
    @Resource
    private FlowLeaveMapper flowLeaveMapper;

    @Resource
    FlowHistoryService flowHistoryService;

    @Resource
    ActTaskService actTaskService;

    @Transactional(readOnly = true)
    public IPage<FlowLeave> findFlowLeaveList(FlowLeaveDto flowLeaveDto, IPage<Void> xPage) {
        return flowLeaveMapper.findPage(xPage, flowLeaveDto);
    }

    @Transactional(readOnly = true)
    public List<FlowLeave> findFlowLeaveList(FlowLeaveDto flowLeaveDto) {
        return flowLeaveMapper.findList(flowLeaveDto);
    }

    @Transactional
    public void saveFlowLeave(FlowLeaveDto flowLeaveDto) {
        FlowLeave flowLeave = DtoConverter.toEntity(flowLeaveDto, FlowLeaveMapStruct.INSTANCE);
        super.saveOrUpdate(flowLeave);
        ProcessInstance processInstance = actTaskService.startProcess(
            flowLeaveDto.getProcessDefinitionId(),
            flowLeave.getTableName(),
            flowLeave.getId().toString()
        );
        flowLeaveDto.setProcessInstanceId(processInstance.getProcessInstanceId());
        saveFlowHistory(flowLeaveDto, flowLeave.getId());
    }

    @Transactional
    public void approve(FlowLeaveDto flowLeaveDto) {
        FlowLeave flowLeave = DtoConverter.toEntity(flowLeaveDto, FlowLeaveMapStruct.INSTANCE);
        super.saveOrUpdate(flowLeave);
        saveFlowHistory(flowLeaveDto, flowLeave.getId());
        actTaskService.complete(
            flowLeaveDto.getTaskId(),
            flowLeaveDto.getProcessInstanceId(),
            flowLeaveDto.getComment(),
            flowLeaveDto.getStatus(),
            null
        );
    }

    private void saveFlowHistory(FlowLeaveDto flowLeaveDto, long busiId) {
        flowHistoryService.saveFlowHistory(
            FlowHistory
                .builder()
                .processInstanceId(flowLeaveDto.getProcessInstanceId())
                .busiId(busiId)
                .busiCode(FlowLeave.CODE)
                .json(JSONUtil.toJsonStr(flowLeaveDto))
                .img(flowLeaveDto.getImg())
                .createUserName(UserUtil.getAuthUser().getName())
                .build()
        );
    }

    @Transactional(readOnly = true)
    public FlowLeave getFlowLeaveById(long id) {
        return flowLeaveMapper.selectById(id);
    }

    @Transactional
    public void deleteFlowLeave(Long id) {
        super.removeById(id);
    }

    @Override
    public FlowLeave getDetailByProcessInstanceId(String processInstanceId) {
        return super.lambdaQuery().eq(FlowLeave::getProcessInstanceId, processInstanceId).one();
    }
}
