package org.javahub.submarine.modules.system.mapstruct;

import org.javahub.submarine.common.base.BaseMapStruct;
import org.javahub.submarine.modules.system.dto.DeptDto;
import org.javahub.submarine.modules.system.entity.Dept;
import org.mapstruct.Mapper;

@Mapper
public interface DeptMapStruct extends BaseMapStruct<DeptDto, Dept> {

}
