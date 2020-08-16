package com.htnova.common.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseTreeEntity<T extends Serializable> extends BaseEntity implements BaseTree<T> {
    /** 父级id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;

    /** 父级ids */
    private String pids;

    /** 子节点 */
    @TableField(exist = false)
    private List<T> children;
}
