package org.javahub.submarine.modules.system.mapstruct;

import org.javahub.submarine.common.base.BaseMapStruct;
import org.javahub.submarine.modules.system.dto.DictionaryDto;
import org.javahub.submarine.modules.system.entity.Dictionary;
import org.mapstruct.Mapper;

@Mapper
public interface DictionaryMapStruct extends BaseMapStruct<DictionaryDto, Dictionary> {

}
