package com.htnova.common.base;

import cn.hutool.core.annotation.AnnotationUtil;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    protected Long id;

    /** 备注 */
    protected String remark;

    /** 创建人 */
    @TableField(fill = FieldFill.INSERT)
    protected Long createBy;

    /** 创建人姓名 */
    protected String creatorName;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    /** 更新人 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Long updateBy;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateTime;

    /** 逻辑删除（0未删除，1已删除） */
    @Builder.Default
    @TableLogic
    protected Integer delFlag = GlobalConst.UNDEL_FLAG;

    @JsonIgnore
    public String getTableName() {
        return AnnotationUtil.getAnnotationValue(this.getClass(), TableName.class);
    }
}
