package org.javahub.submarine.modules.system.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javahub.submarine.common.base.BaseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DictionaryItem extends BaseEntity {


    /**
     * 字典id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long dictionaryId;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 排序
     */
    private Integer sort;


}
