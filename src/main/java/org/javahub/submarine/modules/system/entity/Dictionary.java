package org.javahub.submarine.modules.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.javahub.submarine.common.base.BaseEntity;

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
