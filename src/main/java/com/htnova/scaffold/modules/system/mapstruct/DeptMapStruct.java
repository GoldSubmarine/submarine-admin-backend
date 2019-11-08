package com.htnova.scaffold.modules.system.mapstruct;

import com.htnova.scaffold.common.base.BaseMapStruct;
import com.htnova.scaffold.modules.system.dto.DeptDto;
import com.htnova.scaffold.modules.system.entity.Dept;
import org.mapstruct.Mapper;

@Mapper
public interface DeptMapStruct extends BaseMapStruct<DeptDto, Dept> {

}
