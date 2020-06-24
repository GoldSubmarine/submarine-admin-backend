package com.htnova.system.workflow.dto;

import com.htnova.common.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class FlowHistoryDto extends BaseDto {

    /** 流程实例id */
    private String processInstanceId;

    /** 业务申请的id */
    private Long busiId;

    /** 业务表code */
    private String busiCode;

    /** 页面保存的json数据 */
    private String json;

    /** form截图(base64) */
    private String img;

    /** 创建人姓名 */
    private String createUserName;
}
