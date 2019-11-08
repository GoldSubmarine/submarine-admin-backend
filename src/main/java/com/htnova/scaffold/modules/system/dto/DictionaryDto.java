package com.htnova.scaffold.modules.system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.htnova.scaffold.common.base.BaseDto;
import com.htnova.scaffold.modules.system.entity.Dictionary;
import com.htnova.scaffold.modules.system.mapstruct.DictionaryMapStruct;
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
