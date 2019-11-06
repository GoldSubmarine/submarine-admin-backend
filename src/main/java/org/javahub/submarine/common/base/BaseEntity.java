package org.javahub.submarine.common.base;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.javahub.submarine.common.constant.GlobalConst;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type= IdType.ID_WORKER)
    protected Long id;

    /**
     * 备注
     */
    protected String remark;

    /**
     * 创建人
     */
    @TableField(fill= FieldFill.INSERT)
    protected String createBy;

    /**
     * 创建时间
     */
    @TableField(fill= FieldFill.INSERT)
    protected Date createTime;

    /**
     * 更新人
     */
    @TableField(fill= FieldFill.INSERT_UPDATE)
    protected String updateBy;

    /**
     * 更新时间
     */
//    @TableField(fill= FieldFill.INSERT_UPDATE, update="now()")
    @TableField(fill= FieldFill.INSERT_UPDATE)
    protected Date updateTime;

    /**
     * 逻辑删除（0未删除，1已删除）
     */
    @TableLogic
    protected Integer delFlag = GlobalConst.UNDEL_FLAG;

}
