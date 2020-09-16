package com.htnova.system.manage.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.manage.dto.DictionaryItemDto;
import com.htnova.system.manage.entity.DictionaryItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DictionaryItemMapStruct extends BaseMapStruct<DictionaryItemDto, DictionaryItem> {
    DictionaryItemMapStruct INSTANCE = Mappers.getMapper(DictionaryItemMapStruct.class);
}
