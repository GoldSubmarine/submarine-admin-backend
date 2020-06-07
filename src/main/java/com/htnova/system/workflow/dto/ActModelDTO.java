package com.htnova.system.workflow.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htnova.common.util.DateUtil;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.repository.Model;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActModelDTO {

    /** id */
    private String id;

    /** 名称 */
    private String name;

    /** 唯一标识，基于key升级版本号 */
    private String key;

    /** 分类 */
    private String category;

    /** 版本 */
    private Integer version;

    /** 附加字段（json） */
    private ActModelMeta metaInfo;

    /** 部署id */
    private String deploymentId;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime lastUpdateTime;

    /** 租户id */
    private String tenantId;

    /** xml 模型数据 */
    private String editorSourceValue;

    public ActModelDTO(Model model) {
        ActModelDTO actModelDTO =
                ActModelDTO.builder()
                        .id(model.getId())
                        .name(model.getName())
                        .key(model.getKey())
                        .category(model.getCategory())
                        .version(model.getVersion())
                        .deploymentId(model.getDeploymentId())
                        .createTime(DateUtil.converter(model.getCreateTime()))
                        .lastUpdateTime(DateUtil.converter(model.getLastUpdateTime()))
                        .tenantId(model.getTenantId())
                        .build();

        try {
            ActModelMeta actModelMeta =
                    new ObjectMapper().readValue(model.getMetaInfo(), ActModelMeta.class);
            actModelDTO.setMetaInfo(actModelMeta);
        } catch (Exception e) {
            log.error("反序列化失败：", e);
        }
    }
}
