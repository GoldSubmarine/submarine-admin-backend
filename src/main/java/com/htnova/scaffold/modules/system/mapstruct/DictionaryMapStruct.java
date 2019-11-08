package com.htnova.scaffold.modules.system.mapstruct;

import com.htnova.scaffold.common.base.BaseMapStruct;
import com.htnova.scaffold.modules.system.dto.DictionaryDto;
import com.htnova.scaffold.modules.system.entity.Dictionary;
import org.mapstruct.Mapper;

@Mapper
public interface DictionaryMapStruct extends BaseMapStruct<DictionaryDto, Dictionary> {

}
