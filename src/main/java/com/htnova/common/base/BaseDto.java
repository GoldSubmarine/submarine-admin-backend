package com.htnova.common.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.htnova.common.constant.GlobalConst;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    protected Long id;

    /** 备注 */
    protected String remark;

    /** 创建人 */
    @JsonSerialize(using = ToStringSerializer.class)
    protected Long createBy;

    /** 创建人姓名 */
    protected String creatorName;

    /** 创建时间 */
    protected LocalDateTime createTime;

    /** 更新人 */
    @JsonSerialize(using = ToStringSerializer.class)
    protected Long updateBy;

    /** 更新时间 */
    protected LocalDateTime updateTime;

    /** 逻辑删除（0未删除，1已删除） */
    @Builder.Default
    protected Integer delFlag = GlobalConst.UNDEL_FLAG;
}
