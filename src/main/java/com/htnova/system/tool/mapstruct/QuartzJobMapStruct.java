package com.htnova.system.tool.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.tool.dto.QuartzJobDto;
import com.htnova.system.tool.entity.QuartzJob;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuartzJobMapStruct extends BaseMapStruct<QuartzJobDto, QuartzJob> {
    QuartzJobMapStruct INSTANCE = Mappers.getMapper(QuartzJobMapStruct.class);
}
