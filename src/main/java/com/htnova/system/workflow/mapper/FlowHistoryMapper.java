package com.htnova.system.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.system.workflow.dto.FlowHistoryDto;
import com.htnova.system.workflow.entity.FlowHistory;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FlowHistoryMapper extends BaseMapper<FlowHistory> {

    IPage<FlowHistory> findPage(
            IPage<Void> xPage, @Param("flowHistoryDto") FlowHistoryDto flowHistoryDto);

    List<FlowHistory> findList(@Param("flowHistoryDto") FlowHistoryDto flowHistoryDto);
}
