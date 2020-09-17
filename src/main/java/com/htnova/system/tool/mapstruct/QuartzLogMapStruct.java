package com.htnova.system.tool.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.tool.dto.QuartzLogDto;
import com.htnova.system.tool.entity.QuartzLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuartzLogMapStruct extends BaseMapStruct<QuartzLogDto, QuartzLog> {
    QuartzLogMapStruct INSTANCE = Mappers.getMapper(QuartzLogMapStruct.class);
}
