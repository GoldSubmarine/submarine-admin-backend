package com.htnova.common.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.htnova.common.constant.GlobalConst;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using= ToStringSerializer.class)
    protected Long id;

    /**
     * 备注
     */
    protected String remark;

    /**
     * 创建人
     */
    protected String createBy;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 更新人
     */
    protected String updateBy;

    /**
     * 更新时间
     */
    protected Date updateTime;

    /**
     * 逻辑删除（0未删除，1已删除）
     */
    @Builder.Default
    protected Integer delFlag = GlobalConst.UNDEL_FLAG;

}
