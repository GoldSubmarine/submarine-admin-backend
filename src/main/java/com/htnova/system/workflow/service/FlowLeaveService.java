package com.htnova.system.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.workflow.dto.FlowLeaveDto;
import com.htnova.system.workflow.entity.FlowLeave;
import com.htnova.system.workflow.mapper.FlowLeaveMapper;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowLeaveService extends ActBaseService<FlowLeaveMapper, FlowLeave> {

    @Resource private FlowLeaveMapper flowLeaveMapper;

    @Resource FlowHistoryService flowHistoryService;

    @Resource ActTaskService actTaskService;

    @Transactional(readOnly = true)
    public IPage<FlowLeave> findFlowLeaveList(FlowLeaveDto flowLeaveDto, IPage<Void> xPage) {
        return flowLeaveMapper.findPage(xPage, flowLeaveDto);
    }

    @Transactional(readOnly = true)
    public List<FlowLeave> findFlowLeaveList(FlowLeaveDto flowLeaveDto) {
        return flowLeaveMapper.findList(flowLeaveDto);
    }

    @Transactional
    public void saveFlowLeave(FlowLeave flowLeave) {
        super.saveOrUpdate(flowLeave);
        actTaskService.startProcess(
                flowLeave.getProcessDefinitionId(),
                flowLeave.getTableName(),
                flowLeave.getId().toString());
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
