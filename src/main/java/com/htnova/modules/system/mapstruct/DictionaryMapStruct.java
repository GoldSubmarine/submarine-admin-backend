package com.htnova.modules.system.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.modules.system.dto.DictionaryDto;
import com.htnova.modules.system.entity.Dictionary;
import org.mapstruct.Mapper;

@Mapper
public interface DictionaryMapStruct extends BaseMapStruct<DictionaryDto, Dictionary> {

}
