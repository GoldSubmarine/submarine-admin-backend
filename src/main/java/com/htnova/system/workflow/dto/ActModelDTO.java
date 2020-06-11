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

    /** svg 数据 */
    private String editorSourceExtraValue;

    // 只展示最新版本
    private boolean lastVersion;

    public ActModelDTO(Model model) {
        this.id = model.getId();
        this.name = model.getName();
        this.key = model.getKey();
        this.category = model.getCategory();
        this.version = model.getVersion();
        this.deploymentId = model.getDeploymentId();
        this.createTime = DateUtil.converter(model.getCreateTime());
        this.lastUpdateTime = DateUtil.converter(model.getLastUpdateTime());
        this.tenantId = model.getTenantId();

        try {
            ActModelMeta actModelMeta =
                    new ObjectMapper().readValue(model.getMetaInfo(), ActModelMeta.class);
            this.metaInfo = actModelMeta;
        } catch (Exception e) {
            log.error("反序列化失败：", e);
        }
    }
}
