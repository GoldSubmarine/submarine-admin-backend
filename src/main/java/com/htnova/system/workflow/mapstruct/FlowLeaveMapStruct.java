package com.htnova.system.workflow.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.workflow.dto.FlowLeaveDto;
import com.htnova.system.workflow.entity.FlowLeave;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FlowLeaveMapStruct extends BaseMapStruct<FlowLeaveDto, FlowLeave> {
    FlowLeaveMapStruct INSTANCE = Mappers.getMapper(FlowLeaveMapStruct.class);
}
