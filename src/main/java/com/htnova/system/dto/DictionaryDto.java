package com.htnova.system.dto;

import com.htnova.common.base.BaseEntity;
import com.htnova.system.entity.Dictionary;
import com.htnova.system.mapstruct.DictionaryMapStruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DictionaryDto extends BaseEntity {


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
