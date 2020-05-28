package com.htnova.system.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.htnova.common.base.BaseEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@TableName("t_sys_dictionary")
public class Dictionary extends BaseEntity {
    /** 字典名 */
    private String name;

    @TableField(exist = false)
    private List<DictionaryItem> dictionaryItemList;
}
