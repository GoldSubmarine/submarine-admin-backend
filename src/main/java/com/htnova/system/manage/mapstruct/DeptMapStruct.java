package com.htnova.system.manage.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.manage.dto.DeptDto;
import com.htnova.system.manage.entity.Dept;
import org.mapstruct.Mapper;

@Mapper
public interface DeptMapStruct extends BaseMapStruct<DeptDto, Dept> {}
