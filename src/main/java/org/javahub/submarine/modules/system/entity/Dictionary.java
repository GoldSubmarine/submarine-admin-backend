package org.javahub.submarine.modules.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.common.base.BaseEntity;
import org.javahub.submarine.modules.system.dto.DictionaryDto;
import org.javahub.submarine.modules.system.mapstruct.DictionaryMapStruct;
import org.mapstruct.factory.Mappers;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Dictionary extends BaseEntity {

    /**
     * 字典名
     */
    private String name;


}
