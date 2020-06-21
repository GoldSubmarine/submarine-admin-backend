package com.htnova.system.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.system.workflow.dto.FlowHistoryDto;
import com.htnova.system.workflow.entity.FlowHistory;
import com.htnova.system.workflow.mapper.FlowHistoryMapper;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowHistoryService extends ServiceImpl<FlowHistoryMapper, FlowHistory> {

    @Resource private FlowHistoryMapper flowHistoryMapper;

    @Transactional(readOnly = true)
    public IPage<FlowHistory> findFlowHistoryList(
            FlowHistoryDto flowHistoryDto, IPage<Void> xPage) {
        return flowHistoryMapper.findPage(xPage, flowHistoryDto);
    }

    @Transactional(readOnly = true)
    public List<FlowHistory> findFlowHistoryList(FlowHistoryDto flowHistoryDto) {
        return flowHistoryMapper.findList(flowHistoryDto);
    }

    @Transactional
    public void saveFlowHistory(FlowHistory flowHistory) {
        super.saveOrUpdate(flowHistory);
    }

    @Transactional(readOnly = true)
    public FlowHistory getFlowHistoryById(long id) {
        return flowHistoryMapper.selectById(id);
    }

    @Transactional
    public void deleteFlowHistory(Long id) {
        super.removeById(id);
    }
}
