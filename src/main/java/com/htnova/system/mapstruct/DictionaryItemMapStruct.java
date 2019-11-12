package com.htnova.system.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.dto.DictionaryItemDto;
import com.htnova.system.entity.DictionaryItem;
import org.mapstruct.Mapper;

@Mapper
public interface DictionaryItemMapStruct extends BaseMapStruct<DictionaryItemDto, DictionaryItem> {

}
