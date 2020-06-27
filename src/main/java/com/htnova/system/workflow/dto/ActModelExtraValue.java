package com.htnova.system.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActModelExtraValue {

    /** 图片数据 */
    private String img;

    /** 前端g6绘图用的json * */
    private String g6;
}
