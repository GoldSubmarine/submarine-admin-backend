package com.htnova.modules.system.dto;

import com.htnova.common.base.BaseDto;
import com.htnova.modules.system.entity.Dictionary;
import com.htnova.modules.system.mapstruct.DictionaryMapStruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DictionaryDto extends BaseDto {


    /**
     * 字典名
     */
    private String name;


    public static Dictionary toEntity(DictionaryDto dictionaryDto) {
        DictionaryMapStruct mapStruct = Mappers.getMapper( DictionaryMapStruct.class );
        return mapStruct.toEntity(dictionaryDto);
    }

    public static DictionaryDto toDto(Dictionary dictionary) {
        DictionaryMapStruct mapStruct = Mappers.getMapper( DictionaryMapStruct.class );
        return mapStruct.toDto(dictionary);
    }

}
