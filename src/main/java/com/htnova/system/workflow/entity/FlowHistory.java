package com.htnova.system.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.htnova.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@TableName("t_flow_history")
public class FlowHistory extends BaseEntity {

    /** 流程实例id */
    private String processInstanceId;

    /** 业务申请的id */
    private Long busiId;

    /** 页面保存的json数据 */
    private String json;

    /** form截图(base64) */
    private String img;
}
