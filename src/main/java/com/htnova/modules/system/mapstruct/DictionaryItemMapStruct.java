package com.htnova.modules.system.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.modules.system.dto.DictionaryItemDto;
import com.htnova.modules.system.entity.DictionaryItem;
import org.mapstruct.Mapper;

@Mapper
public interface DictionaryItemMapStruct extends BaseMapStruct<DictionaryItemDto, DictionaryItem> {

}
