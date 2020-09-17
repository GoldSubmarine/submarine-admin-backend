package com.htnova.system.workflow.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.workflow.dto.FlowHistoryDto;
import com.htnova.system.workflow.entity.FlowHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FlowHistoryMapStruct extends BaseMapStruct<FlowHistoryDto, FlowHistory> {
    FlowHistoryMapStruct INSTANCE = Mappers.getMapper(FlowHistoryMapStruct.class);
}
