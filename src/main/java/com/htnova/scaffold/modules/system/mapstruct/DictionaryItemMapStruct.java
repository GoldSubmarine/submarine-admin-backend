package com.htnova.scaffold.modules.system.mapstruct;

import com.htnova.scaffold.common.base.BaseMapStruct;
import com.htnova.scaffold.modules.system.dto.DictionaryItemDto;
import com.htnova.scaffold.modules.system.entity.DictionaryItem;
import org.mapstruct.Mapper;

@Mapper
public interface DictionaryItemMapStruct extends BaseMapStruct<DictionaryItemDto, DictionaryItem> {

}
