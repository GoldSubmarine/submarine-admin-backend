package com.htnova.system.tool.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.tool.dto.QuartzLogDto;
import com.htnova.system.tool.entity.QuartzLog;
import org.mapstruct.Mapper;

@Mapper
public interface QuartzLogMapStruct extends BaseMapStruct<QuartzLogDto, QuartzLog> {}
