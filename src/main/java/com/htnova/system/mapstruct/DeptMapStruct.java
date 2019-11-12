package com.htnova.system.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.dto.DeptDto;
import com.htnova.system.entity.Dept;
import org.mapstruct.Mapper;

@Mapper
public interface DeptMapStruct extends BaseMapStruct<DeptDto, Dept> {

}
