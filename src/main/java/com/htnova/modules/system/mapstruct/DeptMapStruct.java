package com.htnova.modules.system.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.modules.system.dto.DeptDto;
import com.htnova.modules.system.entity.Dept;
import org.mapstruct.Mapper;

@Mapper
public interface DeptMapStruct extends BaseMapStruct<DeptDto, Dept> {

}
