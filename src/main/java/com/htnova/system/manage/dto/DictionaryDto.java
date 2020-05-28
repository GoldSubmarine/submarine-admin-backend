package com.htnova.system.manage.dto;

import com.htnova.common.base.BaseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class DictionaryDto extends BaseDto {
    /** 字典名 */
    private String name;

    private List<DictionaryItemDto> dictionaryItemList;
}
