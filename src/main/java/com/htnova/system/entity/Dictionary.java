package com.htnova.system.entity;

import com.htnova.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class Dictionary extends BaseEntity {

    /**
     * 字典名
     */
    private String name;


}
