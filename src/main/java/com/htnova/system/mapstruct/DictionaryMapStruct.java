package com.htnova.system.mapstruct;

import com.htnova.common.base.BaseMapStruct;
import com.htnova.system.dto.DictionaryDto;
import com.htnova.system.entity.Dictionary;
import org.mapstruct.Mapper;

@Mapper
public interface DictionaryMapStruct extends BaseMapStruct<DictionaryDto, Dictionary> {

}
