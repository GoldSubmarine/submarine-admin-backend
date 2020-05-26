package com.htnova.system.tool.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.tool.dto.LocationDto;
import com.htnova.system.tool.entity.Location;
import org.mapstruct.Mapper;

@Mapper
public interface LocationMapStruct extends BaseMapStruct<LocationDto, Location> {

}
