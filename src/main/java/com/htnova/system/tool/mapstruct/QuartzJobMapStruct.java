package com.htnova.system.tool.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.tool.dto.QuartzJobDto;
import com.htnova.system.tool.entity.QuartzJob;
import org.mapstruct.Mapper;

@Mapper
public interface QuartzJobMapStruct extends BaseMapStruct<QuartzJobDto, QuartzJob> {

}
