package com.htnova.system.manage.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.manage.dto.DictionaryDto;
import com.htnova.system.manage.entity.Dictionary;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DictionaryMapStruct extends BaseMapStruct<DictionaryDto, Dictionary> {
    DictionaryMapStruct INSTANCE = Mappers.getMapper(DictionaryMapStruct.class);
}
