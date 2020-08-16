package com.htnova.system.tool.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.htnova.common.base.BaseTreeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@TableName("t_sys_location")
public class Location extends BaseTreeEntity<Location> {
    /** 当前层次 */
    private Integer deep;

    /** 区域名 */
    private String name;

    /** 区域中心经纬度 */
    private String point;
}
