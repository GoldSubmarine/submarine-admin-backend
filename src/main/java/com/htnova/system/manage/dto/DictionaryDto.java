package com.htnova.system.manage.dto;

import com.htnova.common.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DictionaryDto extends BaseDto {

    /**
     * 字典名
     */
    private String name;

}
