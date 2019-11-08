package com.htnova.scaffold.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.htnova.scaffold.common.base.BaseEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class Dept extends BaseEntity {

    /**
     * 名称（中文）
     */
    private String name;


    /**
     * 编码
     */
    private String code;

    /**
     * 父级id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long pid;

    /**
     * 父级ids
     */
    private String pids;

    /**
     * 子节点
     */
    @TableField(exist=false)
    private List<Dept> children;

    /**
     * 发布修改部门名称的事件
     */
    public UpdateName updateName() {
        return new UpdateName();
    }

    public class UpdateName {
        public String getName() {
            return name;
        }
        public Long getId() {
            return id;
        }
    }

}
