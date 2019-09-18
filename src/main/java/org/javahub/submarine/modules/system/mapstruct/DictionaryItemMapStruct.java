package org.javahub.submarine.modules.system.mapstruct;

import org.javahub.submarine.common.base.BaseMapStruct;
import org.javahub.submarine.modules.system.dto.DictionaryItemDto;
import org.javahub.submarine.modules.system.entity.DictionaryItem;
import org.mapstruct.Mapper;

@Mapper
public interface DictionaryItemMapStruct extends BaseMapStruct<DictionaryItemDto, DictionaryItem> {

}
